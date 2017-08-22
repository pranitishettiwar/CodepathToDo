# Pre-work - *ToDo List*

**ToDo List** is an android app that allows building a todo list and basic todo items management functionality including adding new items, editing and deleting an existing item.

Submitted by: **Praniti Shettiwar**

Time spent: **4** hours spent in total

## User Stories

The following **required** functionality is completed:

* [X] User can **successfully add and remove items** from the todo list
* [X] User can **tap a todo item in the list and bring up an edit screen for the todo item** and then have any changes to the text reflected in the todo list.
* [X] User can **persist todo items** and retrieve them properly on app restart

The following **optional** features are implemented:

* [X] Persist the todo items [into SQLite](http://guides.codepath.com/android/Persisting-Data-to-the-Device#sqlite) instead of a text file
* [ ] Improve style of the todo items in the list [using a custom adapter](http://guides.codepath.com/android/Using-an-ArrayAdapter-with-ListView)
* [ ] Add support for completion due dates for todo items (and display within listview item)
* [ ] Use a [DialogFragment](http://guides.codepath.com/android/Using-DialogFragment) instead of new Activity for editing items
* [ ] Add support for selecting the priority of each todo item (and display in listview item)
* [X] Tweak the style improving the UI / UX, play with colors, images or backgrounds

The following **additional** features are implemented:

* [ ] List anything else that you can get done to improve the app functionality!

## Video Walkthrough

Here's a walkthrough of implemented user stories:

<img src='http://i.imgur.com/FPIvkwy.gif' title='Video Walkthrough' width='' alt='Video Walkthrough' />

GIF created with [LiceCap](http://www.cockos.com/licecap/).

## Project Analysis

As part of your pre-work submission, please reflect on the app and answer the following questions below:

**Question 1:** "What are your reactions to the Android app development platform so far? Compare and contrast Android's approach to layouts and user interfaces in past platforms you've used."

**Answer:** I m really excited and liking development of Android app, it’s great to understand the lifecycle and build the app from scratch. Tutorial provided is very concise and helpful. Also, working in Android studio makes it really simple to get references and methods of the object/views. 

I like the layout for Android as it’s defined in xml and separated from business logic similar to web development, where structure of UI elements is defined in HTML and properties are in CSS with business logic in JS.

Also, android has different layouts like portrait, landscape and bucket sizes for different screens, daytime and night time modes,which makes it interesting and needs creative solutions. I m looking forward to learning all these things in the bootcamp.

**Question 2:** "Take a moment to reflect on the `ArrayAdapter` used in your pre-work. How would you describe an adapter in this context and what is its function in Android? Why do you think the adapter is important? Explain the purpose of the `convertView` in the `getView` method of the `ArrayAdapter`."

**Answer:** ArrayAdapter is a link between data source and UI component, in our case it's ArrayList and ListView. ListView is just the container for all the list items, and ArrayAdapter knows how to draw these list items on screen. As adapters creates the list, they store the data and logic for creating these views.

getView() method in ArrayAdapter `aToDoAdapter` is responsible to inflate the view for particular position asked by ListView `lvItems`

In our app ToDo List, we are using simple text view, so we don't need to customize the arrayAdapter.

Using convertView enhance the performance as we reuse the existing view instead of inflating a new one from layout file.

When ListView uses adapter to populate each row, adapter uses getView to inflate View for every list item. As, adapter uses convertView to recycle old views, we don't have to instatiate new object every time adapter wants to display new list item. This highly boosts the performance.
```
if (convertView == null) {
    view = inflater.inflate(resource, parent, false);
} else {
    view = convertView;
}
```

## License

    Copyright [2017] [Praniti Shettiwar]

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

        http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
