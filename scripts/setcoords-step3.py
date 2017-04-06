#!/usr/bin/python2

import json
import os
import googlemaps

for filename in os.listdir('JSONs'):
    root, ext = os.path.splitext(filename)
    if ext == '.json':
        parsed = ''
        with open('JSONs/' + filename, 'r') as f:
            print '[*] Parsing %s' % (filename)
            parsed = json.loads(f.read())
            if not 'lat' in parsed or not 'lon' in parsed:
               parsed['lat'] = ''
               parsed['lon'] = ''

            with open('Geo-JSONs/' + filename, 'w') as f:
                if 'lat' in parsed and 'lon' in parsed:
                    json.dump(parsed, f)
                    os.remove('JSONs/' + filename)

print '[*] Done!'
