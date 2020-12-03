"""
Creates assorted files for new armor pieces.
"""
import os
import json

project_id = "barmorprog"

assets_dir = os.path.join('src', 'main', 'resources', 'assets')
lang_file = os.path.join(assets_dir, project_id, 'lang', 'en_us.json')
item_path = os.path.join(assets_dir, project_id, 'models', 'item')
item_json = {
  "parent": "item/generated",
  "textures": {
    "layer0": project_id+":item/"
  }
}

os.path.makedirs(lang_file, exist_ok=True)

if not os.path.exists(lang_file):
    lang = json.loads("{}")
else:
    lang = json.load(open(lang_file, 'r'))

material = input('Enter the material name: ').lower()
for piece in ['boots', 'leggings', 'chestplate', 'helmet']:
    item = material + '_' + piece

    path = os.path.join(item_path, piece)
    os.makedirs(path, exist_ok=True)

    out = item_json.copy()
    out['textures']['layer0'] += item
    json.dump(out, open(path, 'w'))

    lang[f"item.{project_id}.{item}"] = item.replace('_', ' ').title()

count = 1
while True:
    json.dump(lang, open(lang_file, 'w'))
    desc = input('Please enter set bonuses. Type nothing or "exit" to skip. -- ')
    if desc in ['', 'exit']:
        break
    lang[f"item.{project_id}.{material}.set{count}"] = desc
    count += 1

count = 1
while True:
    json.dump(lang, open(lang_file, 'w'))
    desc = input('Please enter piece bonuses. Type nothing or "exit" to skip. -- ')
    if desc in ['', 'exit']:
        break
    lang[f"item.{project_id}.{material}.piece{count}"] = desc
    count += 1
