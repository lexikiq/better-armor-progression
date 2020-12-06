"""
Creates assorted files for new armor pieces.
"""
import copy
import json
import os
import random
from PIL import Image, ImageDraw

project_id = "barmorprog"

data_dir = os.path.join('src', 'main', 'resources', 'data', project_id)
assets_dir = os.path.join('src', 'main', 'resources', 'assets')
lang_dir = os.path.join(assets_dir, project_id, 'lang')
lang_file = os.path.join(lang_dir, 'en_us.json')
model_path = os.path.join(assets_dir, project_id, 'models', 'item')
textures_path = os.path.join(assets_dir, project_id, 'textures', 'item')
recipe_path = os.path.join(data_dir, 'recipes')
item_json = {
  "parent": "item/generated",
  "textures": {
    "layer0": project_id+":item/"
  }
}
recipe_json = {
    "type": "minecraft:crafting_shaped",
    "pattern": [],
    "key": {
        "X": {
            "item": "minecraft:blaze_powder"
        }
    },
    "result": {
        "item": "barmorprog:blaze_chestplate",
        "count": 1
    }
}
patterns = {'boots': ['X X', 'X X'],
            'leggings': ['XXX', 'X X', 'X X'],
            'chestplate': ['X X', 'XXX', 'XXX'],
            'helmet': ['XXX', 'X X']}

os.makedirs(lang_dir, exist_ok=True)
os.makedirs(model_path, exist_ok=True)
os.makedirs(textures_path, exist_ok=True)

if not os.path.exists(lang_file):
    lang = json.loads("{}")
else:
    lang = json.load(open(lang_file, 'r'))

material = input('Enter the material name: ').lower()
crafting_ingredient = input('Enter the crafting ingredient: ').lower()
if not crafting_ingredient:
    crafting_ingredient = 'minecraft:dirt'

for piece in ['boots', 'leggings', 'chestplate', 'helmet']:
    item = material + '_' + piece

    # model file
    path = os.path.join(model_path, item+'.json')
    out = copy.deepcopy(item_json)
    out['textures']['layer0'] += item
    json.dump(out, open(path, 'w'))

    # recipe file
    r_path = os.path.join(recipe_path, item+'.json')
    r_out = copy.deepcopy(recipe_json)
    r_out['pattern'] = patterns[piece]
    r_out['key']['X']['item'] = crafting_ingredient
    r_out['result']['item'] = project_id + ':' + item
    json.dump(r_out, open(r_path, 'w'))

    # item name
    lang[f"item.{project_id}.{item}"] = item.replace('_', ' ').title()

    # placeholder textures
    i_out = os.path.join(textures_path, item+'.png')
    if not os.path.exists(i_out):
        empty_sprite = Image.new("RGBA", (16, 16))
        canvas = ImageDraw.Draw(empty_sprite)
        clr = [random.randint(0, 255) for x in range(0, 3)]
        canvas.rectangle([0, 0, 15, 15], fill=(*clr, 255), outline=0)
        empty_sprite.save(i_out, 'PNG', optimize=True)

# yes code is repetitive but i'm lazy!! this is time-saving generator script i'm not putting much effort into it
count = 1
while True:
    json.dump(lang, open(lang_file, 'w'), indent=4)
    desc = input('Please enter set bonuses. Type nothing or "exit" to skip. -- ')
    if desc in ['', 'exit']:
        break
    lang[f"item.{project_id}.{material}.set{count}"] = desc
    count += 1

count = 1
while True:
    json.dump(lang, open(lang_file, 'w'), indent=4)
    desc = input('Please enter piece bonuses. Type nothing or "exit" to skip. -- ')
    if desc in ['', 'exit']:
        break
    lang[f"item.{project_id}.{material}.piece{count}"] = desc
    count += 1
