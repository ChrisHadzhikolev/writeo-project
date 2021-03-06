import React, { Component } from "react";
import ArticleDataService from "../../services/article.service";
import AuthService from "../../services/authentication.service";
import "bootstrap/dist/css/bootstrap.min.css";
import { ButtonGroup, ToggleButton } from "react-bootstrap";
export default class AddArticle extends Component {
  constructor(props) {
    super(props);
    this.handleChange = this.handleChange.bind(this);
    this.saveArticle = this.saveArticle.bind(this);
    this.newArticle = this.newArticle.bind(this);

    this.state = {
      articleTitle: "",
      articleContent: "",
      articlePublished: false,
      articleStatus: 0,
      articlePrice: 0,

      submitted: false,
    };
  }
  handleChange = (e) => {
    let tid;
    if (e.target.id === "") {
      tid = "articleStatus";
    } else {
      tid = e.target.id;
    }
    this.setState({
      [tid]: e.target.value,
    });
    //console.log(tid + " : " + e.target.value);
  };

  saveArticle() {
    let data = {
      articleContent: this.state.articleContent,
      articlePublished: this.state.articlePublished,
      articleStatus: this.state.articleStatus,
      articlePrice: this.state.articlePrice,
      articleTitle: this.state.articleTitle,
      author: {
        id: AuthService.getCurrentUser().id,
      },
    };
    console.log(this.state);

    ArticleDataService.create(data)
      .then((response) => {
        this.setState({
          articleContent: response.data.articleContent,
          articlePublished: response.data.articlePublished,
          articleStatus: response.data.articleStatus,
          articlePrice: response.data.articlePrice,
          articleTitle: response.data.articleTitle,

          submitted: true,
        });
        //console.log(response.data);
      })
      .catch((e) => {
        if(e.response.status === 403){
          window.location.replace('forbidden');
        }
      });
  }

  newArticle() {
    this.setState({
      articleTitle: "",
      articleContent: "",
      articlePublished: false,
      articleStatus: 0,
      articlePrice: 0,

      submitted: false,
    });
  }

  render() {
    return (
      <div className="submit-form">
        {this.state.submitted ? (
          <div>
            <h4>You added new article successfully!</h4>
            <button className="btn btn-dark" onClick={this.newArticle}>
              Add New
            </button>
          </div>
        ) : (
          <div>
            <div className="input-group mb-3">
              <div className="input-group-prepend">
                <span className="input-group-text">Title</span>
              </div>
              <input
                type="text"
                className="form-control"
                id="articleTitle"
                required
                onChange={this.handleChange}
                name="title"
              />
            </div>
            <ButtonGroup
              toggle
              className="mb-2"
              id="articleStatus"
              onChange={this.handleChange}
            >
              {/*<ToggleButton*/}
              {/*  className={"btn btn-primary"}*/}
              {/*  variant={"secondary"}*/}
              {/*  type={"radio"}*/}
              {/*  value={0}*/}
              {/*>*/}
              {/*  Auction*/}
              {/*</ToggleButton>*/}
              <ToggleButton
                className={"btn btn-primary"}
                variant={"secondary"}
                type={"radio"}
                value={1}
              >
                Free
              </ToggleButton>
              <ToggleButton
                className={"btn btn-primary"}
                variant={"secondary"}
                type={"radio"}
                value={2}
              >
                Sale
              </ToggleButton>
              <ToggleButton
                className={"btn btn-primary"}
                variant={"secondary"}
                type={"radio"}
                value={3}
              >
                Not for Sale
              </ToggleButton>
            </ButtonGroup>
            <div className="input-group mb-3">
              <div className="input-group-prepend">
                <span className="input-group-text">€</span>
              </div>
              <input
                type="number"
                className="form-control"
                aria-label="Amount (to the nearest euro)"
                id="articlePrice"
                required
                onChange={this.handleChange}
                name="articlePrice"
              />
              <div className="input-group-append">
                <span className="input-group-text">.00</span>
              </div>
            </div>

            <div className="form-group">
              <div className="input-group">
                <div className="input-group-prepend">
                  <span className="input-group-text">Content</span>
                </div>
                <textarea
                  className="form-control"
                  aria-label="Content"
                  id="articleContent"
                  required
                  onChange={this.handleChange}
                  name="content"
                />
              </div>

              <div className="form-group">
                <input
                  style={{ margin: 5 }}
                  type="checkbox"
                  className="form-check-input"
                  id="articlePublished"
                  required
                  value={true}
                  onChange={this.handleChange}
                  name="published"
                />
                <label
                  className="form-check-label"
                  htmlFor="published"
                  style={{ marginLeft: 20 }}
                >
                  Published
                </label>
              </div>
            </div>

            <button
              onClick={this.saveArticle}
              id="addArticleBtn"
              className="btn btn-primary"
            >
              Submit
            </button>
          </div>
        )}
      </div>
    );
  }
}
