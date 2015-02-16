# Instagram Client Demo

This is an Android demo application for fetching the photos from Google using the [Google Image Search API](https://developers.google.com/image-search/v1/jsondevguide#json_reference).

Time spent: 18 hours spent in total

Completed user stories:

* [x] User can enter a search query that will display a grid of image results from the Google Image API.
* [x] User can click on "settings" which allows selection of advanced search options to filter results
* [x] User can configure advanced search filters such as:
  * [x] Size (small, medium, large, extra-large)
  * [x] Color filter (black, blue, brown, gray, green, etc...)
  * [x] Type (faces, photo, clip art, line art)
  * [x] Site (espn.com)
* [x] Subsequent searches will have any filters applied to the search results
* [x] User can tap on any image in results to see the image full-screen
* [x] User can scroll down "infinitely" to continue loading more image results (up to 8 pages) enough for me to check this one.

* [x] Advanced: Robust error handling, check if internet is available, handle error cases, network failures
* [x] Advanced: Use the ActionBar SearchView or custom layout as the query box instead of an EditText
* [x] Advanced: User can share an image to their friends or email it to themselves
* [x] Advanced: Replace Filter Settings Activity with a lightweight modal overlay
* [x] Advanced: Improve the user interface and experiment with image assets and/or styling and coloring
* [x] Bonus: Use the StaggeredGridView to display improve the grid of image results
* [x] Bonus: User can zoom or pan images displayed in full-screen detail view

Random Implementation Notes:

1. I used SharedPreferences to save the settings so that they survive app restarts. I'm curious if this is the best option.
2. I defined a bunch of constants across Activities, Fragments et al. Again, looking for best practices.

Walkthrough of all user stories:

![Video Walkthrough](GridImageSearchDemo.gif)

GIF created with [LiceCap](http://www.cockos.com/licecap/).
