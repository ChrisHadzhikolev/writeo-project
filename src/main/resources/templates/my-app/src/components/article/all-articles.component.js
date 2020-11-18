import React, { Component } from "react";
import ArticleDataService from "../../services/article.service";
import { Link } from "react-router-dom";

export default class AllArticles extends Component {
    constructor(props) {
        super(props);
        this.retrieveArticles = this.retrieveArticles.bind(this);
        this.refreshList = this.refreshList.bind(this);
        this.setActiveArticle = this.setActiveArticle.bind(this);
        this.removeAllArticles = this.removeAllArticles.bind(this);

        this.state = {
            articles: [],
            currentArticle: null,
            currentIndex: -1
        };
    }

    componentDidMount() {
        this.retrieveArticles();
    }

    retrieveArticles() {
        ArticleDataService.getAll()
            .then(response => {
                this.setState({
                    articles: response.data
                });
                console.log(response.data);
            })
            .catch(e => {
                console.log(e);
            });
    }

    refreshList() {
        this.retrieveArticles();
        this.setState({
            currentArticle: null,
            currentIndex: -1
        });
    }

    setActiveArticle(article, index) {
        this.setState({
            currentArticle: article,
            currentIndex: index
        });
    }

    removeAllArticles() {
        ArticleDataService.deleteAll()
            .then(response => {
                console.log(response.data);
                this.refreshList();
            })
            .catch(e => {
                console.log(e);
            });
    }

    render() {
        const {articles, currentArticle, currentIndex } = this.state;

        return (
            <div className="list row">
                <div className="col-md-6">
                    <h4>Articles List</h4>

                    <ul className="list-group">
                        {articles &&
                        articles.map((article, index) => (
                            <li
                                className={
                                    "list-group-item " +
                                    (index === currentIndex ? "active" : "")
                                }
                                onClick={() => this.setActiveArticle(article, index)}
                                key={index}
                            >
                                {article.articleTitle}
                            </li>
                        ))}
                    </ul>

                    <button
                        className="m-3 btn btn-sm btn-danger"
                        onClick={this.removeAllArticles}
                    >
                        Remove All
                    </button>
                </div>
                <div className="col-md-6">
                    {currentArticle ? (
                        <div>
                            <h4>Article</h4>
                            <div>
                                <label>
                                    <strong>Title:</strong>
                                </label>{" "}
                                {currentArticle.articleTitle}
                            </div>
                            <div>
                                <label>
                                    <strong>Content:</strong>
                                </label>{" "}
                                {currentArticle.articleContent}
                            </div>
                            <div>
                                <label>
                                    <strong>Status:</strong>
                                </label>{" "}
                                {currentArticle.articlePublished ? "Published" : "Pending"}
                            </div>

                            <Link
                                to={"/articles/" + currentArticle.id}
                                className="badge badge-warning"
                            >
                                Edit
                            </Link>
                        </div>
                    ) : (
                        <div>
                            <br />
                            <p>Please click on a Article...</p>
                        </div>
                    )}
                </div>
            </div>
        );
    }
}
