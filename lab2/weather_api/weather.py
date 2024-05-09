import datetime
import numpy as np
from fastapi import FastAPI, Request, HTTPException, Form
from fastapi.responses import HTMLResponse, JSONResponse
from fastapi.templating import Jinja2Templates
import httpx
import asyncio
import os
import requests
from dotenv import load_dotenv
# import html_to_json

load_dotenv()
VC_API_KEY = os.getenv("VC_API_KEY")
OW_API_KEY = os.getenv("OW_API_KEY")
PASSWORD = os.getenv("PASSWORD")

app = FastAPI()
templates = Jinja2Templates(directory="html_files")


@app.get('/', response_class=HTMLResponse, status_code=201)
async def get_basic_form(request: Request):
    return templates.TemplateResponse(request=request, name="index.html")


@app.post('/results', response_class=HTMLResponse, status_code=202)
async def post_form(request: Request, city: str = Form(None), start_date: str = Form(None),
                    end_date: str = Form(None), password: str = Form(None)):

    if password != PASSWORD:
        error_html = "<h1>Error: Authorization Failed</h1><p>Wrong password</p>"
        return HTMLResponse(content=error_html, status_code=401)

    if not city or not city.strip():
        error_html = "<h1>Error: No city given </h1><p>Provide a city name"
        return HTMLResponse(content=error_html, status_code=400)

    if start_date is None:
        print(password, PASSWORD)
        error_html = "<h1>Error: No start date given </h1><p>Provide a start date</p>"
        return HTMLResponse(content=error_html, status_code=401)

    if end_date is None:
        print(password, PASSWORD)
        error_html = "<h1>Error: No end date given </h1><p>Provide a end date</p>"
        return HTMLResponse(content=error_html, status_code=401)

    try:
        current_data = get_current_weather(city)

        temperature_current_celsius = current_data["main"]["temp"] - 273.15
        humidity_current = current_data["main"]["humidity"]
        latitude = current_data["coord"]["lat"]
        longitude = current_data["coord"]["lon"]

        context = await parse_request(city, latitude, longitude, start_date, end_date)

        if isinstance(context, HTMLResponse):
            return context

        context["temperature_current_celsius"] = f"{temperature_current_celsius:.2f}",
        context["humidity_current"] = humidity_current
        print(context)
        return JSONResponse(context)

        # return templates.TemplateResponse(request=request, name="results.html", context=context)

    except requests.RequestException as e:
        return HTMLResponse(content=f"<h1>External service unavailable</h1><p>The external service did not find a response.", status_code=503)
    except KeyError as e:
        return HTMLResponse(content=f"<h1>Data not found</h1><p>{e}</p>", status_code=404)
    except ValueError as e:
        return HTMLResponse(content=f"<h1>Error</h1><p>{e}</p>>", status_code=404)
    except HTTPException as e:
        return HTMLResponse(content=f"<h1>Client error {e.status_code}</h1><p>{e.detail}</p>", status_code=e.status_code)
    except Exception as e:
        return HTMLResponse(content=f"<h1>Internal server error</h1><p>{str(e)}</p>", status_code=500)


def get_current_weather(city):
    print(f"http://api.openweathermap.org/data/2.5/weather?appid={OW_API_KEY}&q={city}")
    response = requests.get(f"http://api.openweathermap.org/data/2.5/weather?appid={OW_API_KEY}&q={city}")
    if response.status_code == 404:
        raise HTTPException(status_code=404, detail=f"<h1>Error: City {city} not found</h1>")
    return response.json()


def check_date_range(start_date, end_date):
    start_datetime = datetime.datetime.fromisoformat(start_date)
    end_datetime = datetime.datetime.fromisoformat(end_date)
    if start_datetime > end_datetime:
        return HTMLResponse(content=f"<h1>Error: Invalid date format or start date later than end date</h1>"
                                    f"<p>Start date cannot be later than end date</p>",
                            status_code=400)
    elif start_datetime == end_datetime:
        return HTMLResponse(content=f"<h1>Error: Invalid date format or start date later than end date</h1>"
                                    f"<p>Start day must be earlier that end day</p>",
                            status_code=400)
    elif start_datetime > datetime.datetime.now():
        return HTMLResponse(content=f"<h1>Error: Invalid date format or start date later than end date</h1>"
                                    f"<p>Start date cannot be in the future</p>",
                            status_code=400)
    elif end_datetime > datetime.datetime.now():
        return HTMLResponse(content=f"<h1>Error: Invalid date format or start date later than end date</h1>"
                                    f"<p>End date cannot be in the future</p>",
                            status_code=400)


def calculate_average_temperature_and_humidity(om_weather, vc_weather):
    tmp = 0
    hmd = 0
    for day in vc_weather:
        tmp += day["temp"]
        hmd += day["humidity"]
    tmp /= len(vc_weather)
    hmd /= len(vc_weather)
    return (np.mean(om_weather) + tmp) / 2, hmd


async def parse_request(city, latitude, longitude, start_date, end_date):
    check_date_response = check_date_range(start_date, end_date)

    if check_date_response is not None:
        return check_date_response

    result = await fetch_data(city, latitude, longitude, start_date, end_date)
    for data in result:
        if data.status_code >= 400:
            raise HTTPException(
                status_code=data.status_code,
                detail=data.text,
            )

    cat_image, om_response, vc_response = result
    om_weather = om_response.json()["hourly"]
    vc_weather = vc_response.json()["days"]
    if not om_weather:
        raise ValueError("Open-meteo found no weather data found for the provided location")

    average_temperature, average_humidity = calculate_average_temperature_and_humidity(om_weather["temperature_2m"],
                                                                                       vc_weather)

    context = {"city": city, "average_temperature": f"{average_temperature:.2f}",
               "average_humidity": f"{average_humidity:.2f}", "cat_image": cat_image.json()[0]["url"]}

    return context


async def fetch_data(city, latitude, longitude, start_date, end_date):
    print(f"https://api.open-meteo.com/v1/forecast?latitude={latitude}&longitude={longitude}&start_date={start_date}&end_date={end_date}&hourly=temperature_2m")
    print(f"https://weather.visualcrossing.com/VisualCrossingWebServices/rest/services/timeline/{city}/{start_date}/{end_date}?unitGroup=metric&include=days&key={VC_API_KEY}&contentType=json")
    urls = [
        "https://api.thecatapi.com/v1/images/search",
        f"https://api.open-meteo.com/v1/forecast?latitude={latitude}&longitude={longitude}&start_date={start_date}&end_date={end_date}&hourly=temperature_2m",
        f"https://weather.visualcrossing.com/VisualCrossingWebServices/rest/services/timeline/{city}/{start_date}/{end_date}?unitGroup=metric&include=days&key={VC_API_KEY}&contentType=json",
    ]
    async with httpx.AsyncClient() as client:
        reqs = [client.get(url) for url in urls]
        result = await asyncio.gather(*reqs)

    return result
