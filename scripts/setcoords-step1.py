#!/usr/bin/python2

import json
import os
import googlemaps

count = 0
API_KEY = 'SECRET'
MAX_REQ = 2500

def register_error(buf, filename):
    with open('error.log', 'a') as f:
        f.write(filename + '\n')
    buf.append(filename)

gmaps = googlemaps.Client(key=API_KEY)

with open('error.log', 'r') as f:
    error = f.readlines()

error = [x.strip() for x in error]

for filename in os.listdir('JSONs'):
    if filename not in error:
        root, ext = os.path.splitext(filename)
        if ext == '.json':
            parsed = ''
            with open('JSONs/' + filename, 'r') as f:
                print '[*] Parsing %s (%d/%d)' % (filename, count + 1, MAX_REQ)
                parsed = json.loads(f.read())
                if not 'lat' in parsed or not 'lon' in parsed:
                    try:
                        result = gmaps.geocode(address=parsed['local'])
                        lat_lng = result[0]['geometry']['location']
                        parsed['lat'] = lat_lng['lat']
                        parsed['lon'] = lat_lng['lng']
                    except IndexError:
                        print '[!] %s could not be parsed! Address not found!' % filename
                        count += 1
                        if count >= MAX_REQ:
                            break
                        register_error(error, filename)
                        continue
                    except KeyError:
                        print '[!] %s doesn\'t have an address!' % filename
                        register_error(error, filename)
                        continue

            with open('Geo-JSONs/' + filename, 'w') as f:
                if 'lat' in parsed and 'lon' in parsed:
                    json.dump(parsed, f)
                    os.remove('JSONs/' + filename)

            count += 1
            if count >= MAX_REQ:
                break
    else:
        print '[!] %s contain errors!' % filename

print '[*] Done!'
