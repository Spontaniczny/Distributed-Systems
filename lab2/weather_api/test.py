from PIL import Image
import requests
from io import BytesIO
from dotenv import load_dotenv
import os

load_dotenv()
API_KEY = os.getenv("VC_API_KEY")

url = "https://thesecatsdonotexist.com"
# url = "https://api.thecatapi.com/v1/images/search"
start_date = "2024-03-15"
end_date = "2024-03-18"
# r = requests.get(url)
r = requests.request("GET", f"https://weather.visualcrossing.com/VisualCrossingWebServices/rest/services/timeline/krak%C3%B3w/{start_date}/{end_date}?unitGroup=metric&key={API_KEY}&contentType=json")
# r = requests.request("GET", f"https://weather.visualcrossing.com/VisualCrossingWebServices/rest/services/timeline/krak%C3%B3w/{start_date}/{end_date}?unitGroup=metric&include=days&key={API_KEY}&contentType=json")



# img = r.json()[0]["url"]
# print(img)


print(r.text)



