import React, { Component } from "react";
import "bootstrap/dist/css/bootstrap.min.css";
import { Link, Redirect } from "react-router-dom";

import Form from "react-validation/build/form";
import Input from "react-validation/build/input";
import CheckButton from "react-validation/build/button";

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

export default class LoginComponent extends Component {
  constructor(props) {
    super(props);
    this.handleLogin = this.handleLogin.bind(this);
    this.handleChange = this.handleChange.bind(this);

    this.state = {
      username: "",
      password: "",
      loading: false,
      message: "",
    };
  }

  handleChange(e) {
    this.setState({
      [e.target.id]: e.target.value,
    });
  }

  handleLogin(e) {
    e.preventDefault();

    this.setState({
      message: "",
      loading: true,
    });

    this.form.validateAll();

    if (this.checkBtn.context._errors.length === 0) {
      AuthService.login(this.state.username, this.state.password).then(
        () => {
          this.props.history.push("/profile");
          window.location.reload();
        },
        (error) => {
          const resMessage =
            (error.response &&
              error.response.data &&
              error.response.data.message) ||
            error.message ||
            error.toString();

          this.setState({
            loading: false,
            message: resMessage,
          });
        }
      );
    } else {
      this.setState({
        loading: false,
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
            className="login-form"
            onSubmit={this.handleLogin}
            ref={(c) => {
              this.form = c;
            }}
          >
            <Input
              type="text"
              placeholder="Username or Email"
              autoFocus={true}
              name="username"
              id="username"
              value={this.state.username}
              onChange={this.handleChange}
              validations={[required]}
            />
            <Input
              type="password"
              placeholder="Password"
              name="password"
              id="password"
              value={this.state.password}
              onChange={this.handleChange}
              validations={[required]}
            />
            <button id="loginBtn" disabled={this.state.loading}>
              {this.state.loading && (
                <span className="spinner-border spinner-border-sm" />
              )}
              <span>Login</span>
            </button>
            {this.state.message && (
              <div className="form-group">
                <div className="alert alert-danger" role="alert">
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
              Not registered?{" "}
              <a>
                <Link to={"/register"}>Create an account</Link>
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
