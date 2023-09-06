import axios from 'axios';

class Login extends React.Component {
  handleSubmit = event => {
    event.preventDefault();
    const email = event.target.email.value;
    axios
      .post('/login', {
        email: email
      })
      .then(response => {
        console.log(response.data);
      });
  };
 
  render() {
    return (
      <form onSubmit={this.handleSubmit}>
        <input type="email" name="email" />
        <button type="submit">Login</button>
      </form>
    );
  }
}
