from kazoo.client import KazooClient
from kazoo.client import KazooState
from kazoo.exceptions import NoNodeError
import sys
import graphviz
from PyQt5 import QtWidgets, QtSvg, QtCore


class ZookeeperApp(QtWidgets.QWidget):
    def __init__(self, hosts='127.0.0.1:2181'):
        super().__init__()
        self.init_ui()
        self.zk = KazooClient(hosts=hosts)
        self.zk.add_listener(self.my_listener)
        self.zk.start()
        self.graphviz_file = 'zookeeper_tree'
        self.check_and_watch_node("/a")

    def init_ui(self):
        self.setWindowTitle('Zookeeper Tree Visualizer')
        self.svg_widget = QtSvg.QSvgWidget()
        layout = QtWidgets.QVBoxLayout()
        layout.addWidget(self.svg_widget)
        self.setLayout(layout)
        self.hide()  # Start hidden

    def my_listener(self, state):
        if state == KazooState.LOST:
            print("Session lost")
        elif state == KazooState.SUSPENDED:
            print("Session suspended")
        elif state == KazooState.CONNECTED:
            print("Connected to Zookeeper")

    def check_and_watch_node(self, path):
        if self.zk.exists(path):
            self.show()
            self.watch_node(path)
        else:
            self.hide()
            self.watch_for_creation(path)

    def watch_for_creation(self, path):
        @self.zk.DataWatch(path)
        def watch_node(data, stat, event):
            if event is not None and event.type == "CREATED":
                print(f"Node {path} has been created. Reopening window...")
                self.watch_node(path)
                QtCore.QMetaObject.invokeMethod(self, "reopen_window", QtCore.Qt.QueuedConnection)
            return True
        return True

    def watch_node(self, path):
        @self.zk.ChildrenWatch(path)
        def watch_children(children):
            print(f"ChildrenWatch event on {path}: {children}")
            QtCore.QMetaObject.invokeMethod(self, "update_graph", QtCore.Qt.QueuedConnection)
            for child in children:
                self.watch_node(f"{path}/{child}")
            return True

        @self.zk.DataWatch(path)
        def watch_node(data, stat, event):
            if event is not None:
                print(f"DataWatch event: {event.type}")
                if event.type == "DELETED" and path == "/a":
                    print("Node /a has been deleted. Closing window...")
                    QtCore.QMetaObject.invokeMethod(self, "close_window", QtCore.Qt.QueuedConnection)
                elif event.type == "CREATED" and path == "/a":
                    print("Node /a has been created. Reopening window...")
                    QtCore.QMetaObject.invokeMethod(self, "reopen_window", QtCore.Qt.QueuedConnection)
                else:
                    QtCore.QMetaObject.invokeMethod(self, "update_graph", QtCore.Qt.QueuedConnection)
            return True
        return True

    @QtCore.pyqtSlot()
    def update_graph(self):
        print("Updating graph")
        dot = graphviz.Digraph(format='svg')
        try:
            self.build_tree(dot, "/a")
            dot.render(self.graphviz_file)
            self.svg_widget.load(self.graphviz_file + '.svg')
            self.svg_widget.repaint()
        except NoNodeError:
            print("Node /a does not exist")
        except Exception as e:
            print(f"Unexpected error: {e}")


    def build_tree(self, dot, path):
        try:
            children = self.zk.get_children(path)
            print(f"Children of {path}: {children}")
            dot.node(path, path)
            for child in children:
                child_path = f"{path}/{child}"
                dot.node(child_path, child)
                dot.edge(path, child_path)
                self.build_tree(dot, child_path)
        except NoNodeError:
            print(f"NoNodeError for path: {path}")
        except Exception as e:
            print(f"Unexpected error in build_tree: {e}")

    @QtCore.pyqtSlot()
    def close_window(self):
        print("Closing window...")
        self.hide()

    @QtCore.pyqtSlot()
    def reopen_window(self):
        print("Reopening window...")
        self.show()
        self.update_graph()

    def closeEvent(self, event):
        print("Stopping Zookeeper client...")
        self.zk.stop()
        self.zk.close()
        event.accept()


def main():
    app = QtWidgets.QApplication(sys.argv)
    zk_app = ZookeeperApp()
    sys.exit(app.exec_())


if __name__ == "__main__":
    main()
