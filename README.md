## MusicWiki App
MusicWiki is an unofficial Last.fm app that contains information about different music genres, the albums, artists, and tracks listed under the genre. This app helps users to explore and discover music genres, albums, artists, and tracks.

### Features
- Display the list of genres available.
- Clicking on the genre takes the user to a page containing information regarding it.
- Information page includes the title, description of the genre, top albums, top tracks, and top artists as different sections in tabLayout.
- Each item listed under albums displays the cover image, title, and artist name.
- Each item listed under artists displays the cover image, name, play count, and followers.
- Each item listed under tracks displays the cover image, title, and artist name.
- Clicking on any item will take the user to a detailed view of that item, including the cover image, description, genres, top tracks, and top albums.
- Clicking on the genre in the detailed view will take the user back to the genre information page.

### Usage
- On launching the app, the user will see a list of top 10 genres available.
- Clicking on the more button will show the entire list of genres on the same screen.
- Clicking on any genre will take the user to a page containing information about that genre, including top albums, top tracks, and top artists.
- Clicking on any album, artist, or track will take the user to a detailed view of that item, including the cover image, description, genres, top tracks, and top albums.
- Clicking on the genre in the detailed view will take the user back to the genre information page.

### Technologies Used
 #### This app was built using the following technologies:
- Android Jetpack Components
- MVVM architecture
- Navigation Components
- Retrofit
- Last.fm API
- Kotlin 
- RecyclerView
- Glide library for image loading
- Dagger-Hilt for dependency injection
- Kotlin Couroutines

### Design Decisions
1. User Interface -
The app's user interface is designed to be simple and intuitive, with a focus on providing the user with the necessary information without overwhelming them with too many details.

2. Architecture -
The app follows MVVM architecture Using the MVVM architecture can improve the code's maintainability by separating concerns and making it easier to modify or update individual components without affecting the entire app.

3. Data Presentation -
The app presents data in a clear and organized way to make it easy for users to browse through different genres, albums, artists, and tracks.

4. Last.fm API -
The app uses the retrofit library and Last.fm API to fetch the relevant data, which includes genres, albums, artists, and tracks.

5. Image Loading -
The app uses the Glide library to load images, making the user interface more visually appealing.

6. Dagger-Hilt -  
The app uses the dagger-Hilt for dependency injection and singleton pattern is implemented very easily.

### Assumptions
- Last.fm API Availability
The app assumes that the Last.fm API is always available and will return the necessary data in the expected format.


### Screenshots


- Home Screen 

<p>
  <img src="https://res.cloudinary.com/dixttklud/image/upload/v1677270840/MusicWiki/Screenshot_20230225_014924_vgsy6b.png" width = 20% height = 20% alt="Image 1" style="margin-right: 20px;">
  <img src="https://res.cloudinary.com/dixttklud/image/upload/v1677270724/MusicWiki/ExpadedList_zargtu.png" width = 20% height = 20% alt="Image 2">
</p>

- Genre Detail Screen

<p>
  <img src="https://res.cloudinary.com/dixttklud/image/upload/v1677270725/MusicWiki/GenreDetailAlbum_k1i7nq.png" width = 20% height = 20% alt="Image 1" style="margin-right: 20px;">
  <img src="https://res.cloudinary.com/dixttklud/image/upload/v1677270725/MusicWiki/GenreDetailArtist_ftso4d.png" width = 20% height = 20% alt="Image 2">
  <img src="https://res.cloudinary.com/dixttklud/image/upload/v1677270724/MusicWiki/GenreDetailTracks_obvron.png" width = 20% height = 20% alt="Image 3">
</p>

- Album Detail Screen

<img src="https://res.cloudinary.com/dixttklud/image/upload/v1677270725/MusicWiki/AlbumDetail_xeuvks.jpg" width = 20% height = 20%>

- Artist Detail Screen
<p>
  <img src="https://res.cloudinary.com/dixttklud/image/upload/v1677270725/MusicWiki/artistDetail1_qmzyog.jpg" width = 20% height = 20% alt="Image 1" style="margin-right: 20px;">
  <img src="https://res.cloudinary.com/dixttklud/image/upload/v1677270725/MusicWiki/artistDetail2_eoofh2.jpg" width = 20% height = 20% alt="Image 2">
</p>





