import json
import unicodedata


REGION_NUMBERS = {
    "tarapaca": 1,
    "antofagasta": 2,
    "atacama": 3,
    "coquimbo": 4,
    "valparaiso": 5,
    "libertador general bernardo ohiggins": 6,
    "maule": 7,
    "biobio": 8,
    "araucania": 9,
    "los lagos": 10,
    "aysen": 11,
    "magallanes y de la antartica chilena": 12,
    "region metropolitana": 13,
    "metropolitana": 13,
    "los rios": 14,
    "arica y parinacota": 15,
    "nuble": 16,
}

REGION_ROMAN = {
    1: "I",
    2: "II",
    3: "III",
    4: "IV",
    5: "V",
    6: "VI",
    7: "VII",
    8: "VIII",
    9: "IX",
    10: "X",
    11: "XI",
    12: "XII",
    13: "RM",
    14: "XIV",
    15: "XV",
    16: "XVI",
}


def normalize_region_name(region_name):

    region_key = normalize_text(region_name)

    region_names = {
        "arica y parinacota": "Región de Arica y Parinacota",
        "tarapaca": "Región de Tarapacá",
        "antofagasta": "Región de Antofagasta",
        "atacama": "Región de Atacama",
        "coquimbo": "Región de Coquimbo",
        "valparaiso": "Región de Valparaíso",
        "metropolitana": "Región Metropolitana",
        "region metropolitana": "Región Metropolitana",
        "libertador general bernardo ohiggins":
            "Región del Libertador General Bernardo O'Higgins",
        "maule": "Región del Maule",
        "nuble": "Región de Ñuble",
        "biobio": "Región del Biobío",
        "araucania": "Región de La Araucanía",
        "los rios": "Región de Los Ríos",
        "los lagos": "Región de Los Lagos",
        "aysen": "Región de Aysén del General Carlos Ibáñez del Campo",
        "magallanes y de la antartica chilena":
            "Región de Magallanes y de la Antártica Chilena",
    }

    return region_names.get(region_key, region_name)

def normalize_text(text):
    text = text.lower().strip()

    text = unicodedata.normalize("NFD", text)

    text = "".join(
        c for c in text
        if unicodedata.category(c) != "Mn"
    )

    text = "".join(
        c for c in text
        if c.isalnum() or c.isspace()
    )

    return " ".join(text.split())


def group_cities_by_region(data):

    data_cleaned = {}

    for item in data:

        region_name = normalize_region_name(item["admin_name"])
        city_name = item["city"]

        region_key = normalize_text(region_name)
        city_key = normalize_text(city_name)

        region_number = REGION_NUMBERS.get(
            normalize_text(item["admin_name"])
        )

        if region_number is None:
            print(f"Región no encontrada: {item['admin_name']}")
            continue

        region_code = REGION_ROMAN[region_number]

        if region_key not in data_cleaned:

            data_cleaned[region_key] = {
                "region_name": region_name,
                "normalized_name": region_key,
                "nro_region": region_number,
                "code_roman": region_code,
                "cities": []
            }

        existing_cities = {
            city["normalized_name"]
            for city in data_cleaned[region_key]["cities"]
        }

        if city_key not in existing_cities:

            data_cleaned[region_key]["cities"].append({
                "name": city_name,
                "normalized_name": city_key
            })

    return list(data_cleaned.values())


with open("cl.json", "r", encoding="utf-8") as file:
    data = json.load(file)


result = group_cities_by_region(data)


with open("cl_cleaned.json", "w", encoding="utf-8") as file:
    json.dump(
        result,
        file,
        ensure_ascii=False,
        indent=4
    )