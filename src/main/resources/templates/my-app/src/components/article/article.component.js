import React, { Component } from "react";
import ArticleDataService from "../../services/article.service";

export default class Article extends Component {
    constructor(props) {
        super(props);
        this.handleChange = this.handleChange.bind(this);
        this.getArticle = this.getArticle.bind(this);
        this.updatePublished = this.updatePublished.bind(this);
        this.updateArticle = this.updateArticle.bind(this);
        this.deleteArticle = this.deleteArticle.bind(this);

        this.state = {
            currentArticle: {
                id:null,
                articleTitle: "",
                articleContent: "",
                articlePublished: false,
                // articleStatus: 0,
                articlePrice: 0,
            },
            message: ""
        };
    }

    componentDidMount() {
        this.getArticle(this.props.match.params.id);
        this.setState({
                id : this.props.match.params.id
        }
        )
    }

    handleChange = (e) => {
        const val = e.target.value;
        const tid = e.target.id;
        console.log(val + " " + tid)
        this.setState(prevState => ({
            currentArticle: {
                ...prevState.currentArticle,
                [tid]: val
            }
        }));
        console.log(val + " " + tid)
    }


    getArticle(id) {
        ArticleDataService.get(id)
            .then(response => {
                this.setState({
                    currentArticle: response.data
                });
                console.log(response.data);
            })
            .catch(e => {
                console.log(e);
            });
    }

    updatePublished(status) {
        var data = {
            id : this.state.currentArticle.id,
            articleTitle: this.state.currentArticle.articleTitle,
            articleContent: this.state.currentArticle.articleContent,
            articlePublished: status,
            // articleStatus: this.state.currentArticle.articleStatus,
        };


        ArticleDataService.update(this.state.currentArticle.id, data)
            .then(response => {
                this.setState(prevState => ({
                    currentArticle: {
                        ...prevState.currentArticle,
                        articlePublished: status
                    }
                }));
                console.log(response.data);
            })
            .catch(e => {
                console.log(e);
            });
    }

    updateArticle() {
        ArticleDataService.update(
            this.state.currentArticle.id,
            this.state.currentArticle
        )
            .then(response => {
                console.log(response.data);
                this.setState({
                    message: "The article was updated successfully!"
                });
            })
            .catch(e => {
                console.log(e);
            });
    }

    deleteArticle() {
        console.log(this.state.currentArticle.id);
        ArticleDataService.delete(this.state.currentArticle.id)
            .then(response => {
                console.log(response.data);
                this.props.history.push('/articles')
            })
            .catch(e => {
                console.log(e);
            });
    }

    render() {
        let { currentArticle } = this.state;
        return(
        <div>
            {currentArticle ? (
                <div className="edit-form">
                    <h4>Article</h4>
                    <form>
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
                                value={currentArticle.articleTitle}
                                name="title"/>
                        </div>
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
                                value={currentArticle.articlePrice}
                                name="articlePrice"/>
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
                                    value={currentArticle.articleContent}
                                    name="content"/>
                            </div>
                        </div>

                        <div className="form-group">
                            <label>
                                <strong>Status:</strong>
                            </label>
                            {currentArticle.articlePublished ? "Published" : "Not Published"}
                        </div>
                    </form>

                    {currentArticle.articlePublished ? (
                        <button
                            className="badge badge-primary mr-2"
                            onClick={() => this.updatePublished(false)}
                        >
                            UnPublish
                        </button>
                    ) : (
                        <button
                            className="badge badge-primary mr-2"
                            onClick={() => this.updatePublished(true)}
                        >
                            Publish
                        </button>
                    )}

                    <button
                        className="badge badge-danger mr-2"
                        onClick={this.deleteArticle}
                    >
                        Delete
                    </button>

                    <button
                        type="submit"
                        className="badge badge-success"
                        onClick={this.updateArticle}
                    >
                        Update
                    </button>
                    <p>{this.state.message}</p>
                </div>
            ) : (
                <div>
                    <br />
                    <p>Please click on a Article...</p>
                </div>
            )}
        </div>
    );
}
}
