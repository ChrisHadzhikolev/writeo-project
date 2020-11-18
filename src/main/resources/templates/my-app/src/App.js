import React, {Component} from 'react';
import { Switch, Route, Link } from "react-router-dom";
import './App.css';
import "bootstrap/dist/css/bootstrap.min.css";

import AddArticle from "./components/article/add-article.component";
import Article from "./components/article/article.component";
import AllArticles from "./components/article/all-articles.component";
import LoginComponent from "./components/user/login.component";
import RegisterComponent from "./components/user/register.component";
import Profile from "./components/user/profile.component";
import OwnerComponent from "./components/user/owner.component";
import AuthorComponent from "./components/user/author.component";
import AdminComponent from "./components/user/admin.component";
import Home from "./components/home.component";


import AuthService from "./services/authentication.service";


class App extends Component{
  constructor(props) {
    super(props);
    this.logOut = this.logOut.bind(this);

    this.state = {
      showAdmin: false,
      showOwner: false,
      currentUser: undefined,
    };
  }

  componentDidMount() {
    const user = AuthService.getCurrentUser();

    if (user) {
      this.setState({
        currentUser: user,
        showModeratorBoard: user.roles.includes("ROLE_OWNER"),
        showAdminBoard: user.roles.includes("ROLE_ADMIN"),
      });
    }
  }

  logOut() {
    AuthService.logout();
  }

  render() {
    const { currentUser, showAdmin, showOwner } = this.state;

    return(
        <div>
          <nav className="navbar navbar-expand navbar-light bg-white">
            <a className="navbar-brand">
              <img src={require("./images/logo.png")} alt={""} id={"homeBtn"}/>
              <Link id="homeLink" to={"/home"}>Writeo</Link>
            </a>
            {currentUser ? (
                 <div className="navbar">
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

                  <div className="navbar-nav ml-auto">
                    <li className="nav-item">
                      <Link to={"/profile"} className="nav-link">
                        {currentUser.username}
                      </Link>
                    </li>
                    <li className="nav-item">
                      <a href="/login" className="nav-link" onClick={this.logOut}>
                        Log Out
                      </a>
                    </li>
                  </div>
                </div>
            ) : (
                <div className="navbar-nav ml-auto">
                  <li className="nav-item">
                    <Link to={"/login"} className="nav-link">
                      Login
                    </Link>
                  </li>

                  <li className="nav-item">
                    <Link to={"/register"} className="nav-link">
                      Sign Up
                    </Link>
                  </li>
                </div>
            )}
          </nav>
          <hr style={{margin:0}}/>
          <div className="container mt-3">
            <Switch>
              <Route exact path={["/", "/home"]} component={Home} />
              <Route exact path="/login" component={LoginComponent} />
              <Route exact path="/register" component={RegisterComponent} />
              <Route exact path="/profile" component={Profile} />
              <Route path="/author" component={AuthorComponent} />
              <Route path="/owner" component={OwnerComponent} />
              <Route path="/admin" component={AdminComponent} />
              <Route exact path={["/articles"]} component={AllArticles} />
              <Route exact path="/add" component={AddArticle} />
              <Route path="/articles/:id" component={Article} />
            </Switch>
          </div>
        </div>
    );
  }
}

export default App;
