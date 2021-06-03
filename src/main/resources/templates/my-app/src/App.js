import React, { Component } from "react";
import { Switch, Route, Link } from "react-router-dom";
import "./App.css";
import "bootstrap/dist/css/bootstrap.min.css";

import AddArticle from "./components/article/add-article.component";
import Article from "./components/article/article.component";
import AllArticles from "./components/article/all-articles.component";
import LoginComponent from "./components/user/login.component";
import RegisterComponent from "./components/user/register.component";
import Profile from "./components/user/profile.component";
import Home from "./components/home.component";
import Buyer from "./components/buyer/buyer.component"
import ForbiddenComponent from "./components/access/forbidden.component";

import AuthService from "./services/authentication.service";
import {Nav, Navbar, NavDropdown} from "react-bootstrap";
import RevenueRecordsComponent from "./components/revenue/revenue-records.component";
import SellsComponent from "./components/sell/sells.component";
import AuthorsComponent from "./components/author/authors.component";
import InternalServerErrorComponent from "./components/access/internal-server-error.component";
import AdminComponent from "./components/admin.component";
import BuyDialog from "./components/buyDialog.component";

class App extends Component {
  constructor(props) {
    super(props);
    this.logOut = this.logOut.bind(this);

    this.state = {
      currentUser: undefined,
      adminPrivileges: false,
    };
  }

  componentDidMount() {
    const user = AuthService.getCurrentUser();

    if (user) {
      const role = user.roles[0];
      this.setState({
        currentUser: user,
      });
      if (role === "ROLE_ADMIN" || role === "ROLE_OWNER") {
        //console.log(role)
        this.setState({
          adminPrivileges: true,
        });
      }
    }
  }

  logOut() {
    AuthService.logout();
  }

  renderContent() {
    if (this.state.adminPrivileges) {
      return (
          <div className="navbar-nav mr-auto">
            <li className="nav-item">
              <Link to={"/authors"} className="nav-link">
                Authors
              </Link>
            </li>
            <li className="nav-item">
              <Link to={"/admins"} className="nav-link">
                Admins
              </Link>
            </li>
            <li className="nav-item">
              <Link to={"/buyers"} className="nav-link">
                Buyers
              </Link>
            </li>
            {/*<li className="nav-item">*/}
            {/*  <Link to={"/revenue"} className="nav-link">*/}
            {/*    Revenue*/}
            {/*  </Link>*/}
            {/*</li>*/}
            <li className="nav-item">
              <Link to={"/sells"} className="nav-link">
                Sells
              </Link>
            </li>
          </div>
      );
    }
    return (
      <div className="navbar-nav mr-auto">
        <li className="nav-item">
          <Link to={"/articles"} className="nav-link">
            Articles
          </Link>
        </li>
        <li className="nav-item">
          <Link to={"/add"} className="nav-link">
            New Article
          </Link>
        </li>
      </div>
    );
  }

  render() {
    const currentUser = this.state.currentUser;

    return (
      <div>
        <Navbar className="navbar navbar-expand navbar-light bg-white">
          <a className="navbar-brand">
            <img src={require("./images/logo.png")} alt={""} id={"homeBtn"} />
            <Link id="homeLink" to={"/home"}>
              Writeo
            </Link>
          </a>

          {currentUser ? (
            <>
              <Nav className="mr-auto">
                <div className="md-form mt-0">
                  {this.renderContent()}
                </div>
              </Nav>
              <Nav>
                <div className="navbar-nav">
                  <li className="nav-item">
                    <NavDropdown
                        id={"basic-nav-dropdown"}
                        title={currentUser.username}
                    >
                      <NavDropdown.Item>
                        <Link to={"/profile"} className="nav-link">
                          My Profile
                        </Link>
                      </NavDropdown.Item>
                      {/*<NavDropdown.Item>*/}
                      {/*  <Link to={"/newPassword"} className="nav-link">*/}
                      {/*    Change Password*/}
                      {/*  </Link>*/}
                      {/*</NavDropdown.Item>*/}
                      {/*<NavDropdown.Item>*/}
                      {/*  <Link to={"/picture"} className="nav-link">*/}
                      {/*    Profile Picture*/}
                      {/*  </Link>*/}
                      {/*</NavDropdown.Item>*/}
                    </NavDropdown>
                  </li>

                  <li className="nav-item">
                    <a
                        href="/login"
                        id={"logout"}
                        className="nav-link"
                        onClick={this.logOut}
                    >
                      Log Out
                    </a>
                  </li>
                </div>
              </Nav>
            </>
          ) : (
            <div className="navbar-nav ml-auto">
              <li className="nav-item">
                <Link to={"/login"} id="login_link" className="nav-link">
                  Login
                </Link>
              </li>

              <li className="nav-item">
                <Link to={"/register"} id="register_link" className="nav-link">
                  Sign Up
                </Link>
              </li>
            </div>
          )}
        </Navbar>
        <hr style={{ margin: 0 }} />

        <div className="container mt-3">
          <Switch>
            <Route exact path={["/", "/home"]} component={Home} />
            <Route exact path="/forbidden" component={ForbiddenComponent} />
            <Route exact path="/internal" component={InternalServerErrorComponent} />
            <Route exact path="/authors" component={AuthorsComponent} />
            <Route exact path="/admins" component={AdminComponent} />
            <Route exact path="/revenue" component={RevenueRecordsComponent} />
            <Route exact path="/sells" component={SellsComponent} />
            <Route exact path="/login" component={LoginComponent} />
            <Route exact path="/register" component={RegisterComponent} />
            <Route exact path="/profile" component={Profile} />
            <Route exact path={["/articles"]} component={AllArticles} />
            <Route exact path="/add" component={AddArticle} />
            <Route path="/articles/:id" component={Article} />
            <Route path="/buyers" component={Buyer} />
          </Switch>
        </div>
      </div>
    );
  }
}

export default App;
