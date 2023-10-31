Music Library System
This API allows you to manage songs and reviews.

## Endpoints

### GET /songs
Retrieve all songs.

### POST /songs
Add a new song. The request body should contain the details of the song in JSON format.

### GET /songs/{id}
Retrieve a song by its ID. Replace `{id}` with the ID of the song.

### PUT /songs/{id}
Update a song by its ID. Replace `{id}` with the ID of the song. The request body should contain the updated details of the song in JSON format.

### DELETE /songs/{id}
Delete a song by its ID. Replace `{id}` with the ID of the song.

### GET /songs/genre/{genre}
Retrieve songs by genre. Replace `{genre}` with the genre of the songs.

### GET /songs/artist/{artist}
Retrieve songs by artist. Replace `{artist}` with the name of the artist.

### POST /songs/{id}/reviews
Add a review for a song. Replace `{id}` with the ID of the song. The request body should contain the details of the review in JSON format.

### GET /songs/{id}/reviews
Retrieve all reviews of a song. Replace `{id}` with the ID of the song.

### GET /songs/album/{album}
Retrieve songs by album. Replace `{album}` with the name of the album.
