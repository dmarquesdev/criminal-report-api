# A PLATFORM TO ENRICH, EXPAND AND PUBLISH LINKED DATA OF POLICE REPORTS

This project is a enhancement of the API made in the [A PLATFORM TO ENRICH, EXPAND AND PUBLISH LINKED DATA OF POLICE REPORTS](https://doi.org/10.6084/m9.figshare.3856074.v5) article.

Some new features are planned, and the main goal of this version is to be used along with [Isomorphic JavaScript](https://github.com/dmarquesdev/isomorphic-javascript) as the main source of data.

All credits goes to the authors of the article.

The article can be found [Here](https://www.researchgate.net/publication/312937816_A_Platform_to_Enrich_Expand_and_Publish_Linked_Data_of_Police_Reports)

## New Features
* Lat/Lon along with data (info scrapped from Google Geolocation API).
* Filtering results with "contains" mode (using '.*' RegExp metachars).
* 'points' Endpoint that gives only id, lat and lon for improved map rendering.
* Semantic Data is conditionally generated.

## Planned Features
* Disable Google Geocode API request for each data on API request.
