import React, { useState, useEffect } from 'react';
import axios from 'axios';

const App = () => {
  const [stories, setStories] = useState([]);

  useEffect(() => {
    const fetchStories = async () => {
      try {
        const response = await axios.get('http://localhost:8080/api/stories');
        setStories(response.data);
      } catch (error) {
        console.error(error);
      }
    };

    fetchStories();
  }, []);

  return (
    <div>
      {stories.map((story) => (
        <div key={story.id}>
          <h3>{story.title}</h3>
          <p>Story points: {story.storyPoints}</p>
        </div>
      ))}
    </div>
  );
};

export default App;
