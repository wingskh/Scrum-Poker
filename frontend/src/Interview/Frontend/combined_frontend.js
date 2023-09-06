import React, { useState, useEffect } from "react";
import axios from "axios";

const Stories = () => {
  const [stories, setStories] = useState([]);

  useEffect(() => {
    axios
      .get("/api/stories")
      .then(response => {
        setStories(response.data);
      })
      .catch(error => {
        console.error(error);
      });
  }, []);

  return (
    <ul>
      {stories.map(story => (
        <li key={story.id}>{story.title}</li>
      ))}
    </ul>
  );
};

export default Stories;

// In this example, the backend exposes a REST API at the /api/stories endpoint. 
// The frontend makes a GET request to this endpoint to retrieve a list of stories, which it then displays on the page. 
// The use of the useEffect hook ensures that the GET request is made only once when the component is first rendered.