import React, { useState, useEffect } from 'react';
import axios from 'axios';

const UserStories = () => {
  const [stories, setStories] = useState([]);
  const [activeStory, setActiveStory] = useState(null);
  const [userPoints, setUserPoints] = useState({});

  useEffect(() => {
    const fetchData = async () => {
      const result = await axios.get('http://localhost:8080/api/stories');
      setStories(result.data);
    };

    fetchData();
  }, []);

  const handleActiveStoryChange = (event) => {
    setActiveStory(event.target.value);
  };

  const handleUserPointChange = (event) => {
    setUserPoints({ ...userPoints, [activeStory]: event.target.value });
  };

  const handleSubmit = async (event) => {
    event.preventDefault();
    const result = await axios.post('http://localhost:8080/api/stories', {
      story: activeStory,
      userPoint: userPoints[activeStory],
    });
    console.log(result);
  };

  return (
    <div>
      <h1>Active Story: {activeStory}</h1>
      <form onSubmit={handleSubmit}>
        <select onChange={handleActiveStoryChange}>
          <option value="">Select a story</option>
          {stories.map((story) => (
            <option key={story} value={story}>
              {story}
            </option>
          ))}
        </select>
        <br />
        <br />
        <select onChange={handleUserPointChange}>
          <option value="">Select a story point</option>
          <option value="1">1</option>
          <option value="2">2</option>
          <option value="3">3</option>
          <option value="5">5</option>
          <option value="8">8</option>
          <option value="13">13</option>
          <option value="no_idea">no_idea</option>
          <option value="resign">resign</option>
        </select>
        <br />
        <br />
        <button type="submit">Submit</button>
      </form>
      <br />
      <br />
      <h2>Story Points</h2>
      {Object.entries(userPoints).map(([story,
