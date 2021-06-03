import React, { Component } from "react";
import Form from "react-validation/build/form";
import Input from "react-validation/build/input";
import CheckButton from "react-validation/build/button";
import { Link, Redirect } from "react-router-dom";
import { isEmail } from "validator";

import AuthService from "../../services/authentication.service";

const required = (value) => {
  if (!value) {
    return (
      <div className="alert alert-danger" role="alert">
        This field is required!
      </div>
    );
  }
};

const email = (value) => {
  if (!isEmail(value)) {
    return (
      <div className="alert alert-danger" role="alert">
        This is not a valid email.
      </div>
    );
  }
};

const vusername = (value) => {
  if (value.length < 3 || value.length > 20) {
    return (
      <div className="alert alert-danger" role="alert">
        The username must be between 3 and 20 characters.
      </div>
    );
  }
};

const vpassword = (value) => {
  if (
    value.length < 8 ||
    value.length >
      40 /*|| !value.matches("/^(?=.*[\\d])(?=.*[A-Z])(?=.*[a-z])(?=.*[!@#$%^&*])[\\w!@#$%^&*]{8,}$/.test(str)")*/
  ) {
    return (
      <div className="alert alert-danger" role="alert">
        The password must be between 8 and 40 characters.
      </div>
    );
  }
};
export default class RegisterComponent extends Component {
  constructor(props) {
    super(props);
    this.handleRegister = this.handleRegister.bind(this);
    this.handleChange = this.handleChange.bind(this);

    this.state = {
      username: "",
      email: "",
      password: "",
      cpassword: "",
      successful: false,
      message: "",
    };
  }

  handleChange(e) {
    this.setState({
      [e.target.id]: e.target.value,
    });
  }

  handleRegister(e) {
    e.preventDefault();

    if (this.state.password === this.state.cpassword) {
      this.setState({
        message: "",
        successful: false,
      });

      this.form.validateAll();

      if (this.checkBtn.context._errors.length === 0) {
        AuthService.register(
          this.state.username,
          this.state.email,
          this.state.password
        ).then(
          (response) => {
            this.setState({
              message: response.data.message,
              successful: true,
            });
          },
          (error) => {
            const resMessage =
              (error.response &&
                error.response.data &&
                error.response.data.message) ||
              error.message ||
              error.toString();

            this.setState({
              successful: false,
              message: resMessage,
            });
          }
        );
      }
    } else {
      this.setState({
        successful: false,
        message: "Passwords don't match",
      });
    }
  }

  contentChecker() {
    if (AuthService.getCurrentUser() != null) {
      return <Redirect to={"/forbidden"} />;
    }
    return (
      <div className="login-page">
        <div className="form">
          <Form
            className="register-form"
            autoComplete="off"
            onSubmit={this.handleRegister}
            ref={(c) => {
              this.form = c;
            }}
          >
            {!this.state.successful && (
              <div>
                <Input
                  type="text"
                  autoComplete="off"
                  placeholder="Username"
                  id="username"
                  value={this.state.username}
                  onChange={this.handleChange}
                  validations={[required, vusername]}
                />
                {/*<input type="text" placeholder="Gender(will be made combo box)" />*/}

                <Input
                  type="email"
                  autoComplete="off"
                  placeholder="Email"
                  id="email"
                  value={this.state.email}
                  onChange={this.handleChange}
                  validations={[required, email]}
                />
                <Input
                  type="password"
                  autoComplete="new-password"
                  placeholder="Password"
                  id="password"
                  name="password"
                  value={this.state.password}
                  onChange={this.handleChange}
                  validations={[required, vpassword]}
                />
                <Input
                  type="password"
                  autoComplete="new-password"
                  placeholder="Confirm Password"
                  id="cpassword"
                  name="cpassword"
                  value={this.state.cpassword}
                  onChange={this.handleChange}
                  validations={[required, vpassword]}
                />
                <button id="createBtn">create account</button>
              </div>
            )}
            {this.state.message && (
              <div className="form-group">
                <div
                  className={
                    this.state.successful
                      ? "alert alert-success"
                      : "alert alert-danger"
                  }
                  role="alert"
                >
                  {this.state.message}
                </div>
              </div>
            )}
            <CheckButton
              style={{ display: "none" }}
              ref={(c) => {
                this.checkBtn = c;
              }}
            />
            <p className="message">
              Already registered?{" "}
              <a>
                <Link to={"/login"}>Sign in</Link>
              </a>
            </p>
          </Form>
        </div>
      </div>
    );
  }

  render() {
      return(
          <>
              {this.contentChecker()}
          </>
          );
  }
}
