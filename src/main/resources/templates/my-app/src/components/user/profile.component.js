import React, { Component } from "react";
import Form from "react-validation/build/form";
import Input from "react-validation/build/input";
import UserService from "../../services/user.service";
import AuthService from "../../services/authentication.service";

export default class Profile extends Component {
  constructor(props) {
    super(props);

    this.handleChange = this.handleChange.bind(this);
    this.getUser = this.getUser.bind(this);
    this.updateUser = this.updateUser.bind(this);
    this.deleteUser = this.deleteUser.bind(this);

    this.state = {
      currentUser: {
        bio: "",
        firstName: "",
        lastName: "",
        email: "",
        gender: 3,
      },
    };
  }

  componentDidMount() {
    this.getUser();
  }

  handleChange = (e) => {
    const val = e.target.value;
    const tid = e.target.id;
   // console.log(val + " " + tid)
    if(tid === 'genderComboBox'){
      this.setState((prevState) => ({
        currentUser: {
          ...prevState.currentUser,
          gender: val,
        },
      }));
    }else{
      this.setState((prevState) => ({
        currentUser: {
          ...prevState.currentUser,
          [tid]: val,
        },
      }));
    }
  }

  updateUser() {
    if (this.state.currentUser.gender < 0 || this.state.currentUser.gender > 3) {this.state.currentUser.gender = null;}
         UserService.update(this.state.currentUser.id, this.state.currentUser)
          .then((response) => {
            console.log(response.data);
            window.location.reload();
          })
          .catch((e) => {
            console.log(e);
          });
  }

  deleteUser() {
    if (
      window.confirm(
        "Are you sure you want to delete your profile? You won't be able to recover it!"
      )
    ) {
      UserService.delete(this.state.currentUser.id)
        .then((response) => {
          console.log(response.data);
          var logoutBtn = document.getElementById("logout");
          logoutBtn.click();
        })
        .catch((e) => {
          console.log(e);
        });
    }
  }

  getUser() {
    UserService.get(AuthService.getCurrentUser().id)
      .then((response) => {
        console.log(response.data);
        this.setState({
          currentUser: response.data,
        });
        let element = document.getElementById("genderComboBox");
        switch (this.state.currentUser.gender){
          case "Male":
            element.value=0;
            break;
          case "Female":
            element.value=1;
            break;
          case "Other":
            element.value=2;
            break;
          case "Rather Not Say":
            element.value=3;
            break;
          default:
            element.value=-1;
            break;
        }
        console.log(this.state);

      })
      .catch((e) => {
        console.log(e);
      });
  }

  Gender(){
    let element = document.getElementById("genderComboBox");
    console.log(this.state.currentUser.gender);
    element.value = this.state.currentUser.gender;
  }

  render() {
    const { currentUser } = this.state;

    return (
      <div className="profile-page">
        <div className="form">
          <p id={"title"}>My Profile</p>
          <button id="deleteBtn" onClick={this.deleteUser}>
            <span>Delete Profile</span>
          </button>
          <Form className="login-form">
            <textarea
              placeholder="Bio"
              name="bio"
              id="bio"
              value={currentUser.bio}
              onChange={this.handleChange}
            />
            <Input
              type="text"
              placeholder="First Name"
              name="firstName"
              id="firstName"
              value={currentUser.firstName}
              onChange={this.handleChange}
            />
            <Input
              type="text"
              placeholder="Last Name"
              name="lastName"
              id="lastName"
              value={currentUser.lastName}
              onChange={this.handleChange}
            />
            <Input
              type="text"
              placeholder="Email"
              name="email"
              id="email"
              value={currentUser.email}
              onChange={this.handleChange}
            />
            <select className = "combobox" onChange = {this.handleChange} id={"genderComboBox"}>
              <option value={-1} selected disabled hidden>
                Gender
              </option>
              <option value={0}>Male</option>
              <option value={1}>Female</option>
              <option value={2}>Other</option>
              <option value={3}>Rather Not Say</option>
            </select>
            <button disabled={this.state.loading} onClick={this.updateUser}>
              {this.state.loading && (
                <span className="spinner-border spinner-border-sm" />
              )}
              <span>Update Details</span>
            </button>

          </Form>
        </div>
      </div>
    );
  }
}
