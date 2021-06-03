import React, { Component } from "react";
import ArticleDataService from "../services/article.service";
import UserDataService from "../services/user.service"
import BuyDialog from "./buyDialog.component";

export default class Home extends Component {
  constructor(props) {
    super(props);

    this.retrieveAvailableArticles = this.retrieveAvailableArticles.bind(this);
    this.getAuthorDetails = this.getAuthorDetails.bind(this);
    this.passArticle = this.passArticle.bind(this);

    this.state = {
      articles: [],
      articleP:undefined
    };
  }

  componentDidMount() {
    this.retrieveAvailableArticles();
  }

  retrieveAvailableArticles = () => {
    ArticleDataService.getAllAvailable()
      .then((response) => {
        this.setState({
          articles: response.data,
        });
      })
      .catch((err) => {
        console.log(err)
        // if (err.response.status === 500) {
        //   window.location.replace("/internal");
        // }
      });
  };

  getAuthorDetails = (id) =>{
    UserDataService.getShopAuthor(id).then(response=>{
      console.log(response.data.firstName);
      if(response.data.firstName === null && response.data.lastName === null){
        return 'Unknown';
      }else{
        return response.data.firstName + " " + response.data.lastName;

      }
    }).catch(err =>{
      console.log(err);
      if (err.response.status === 500) {
        window.location.replace("/internal");
      }
    });
  }
  passArticle = (passedArticle) =>{
    this.state.articleP = passedArticle;
  }

  render() {
    const { articles } = this.state;
    return (
      <>
        {articles &&
          articles.map((article) => (
              <center>
                <div className="card" style={{ width: "50rem" }}>
                  <div className="card-body">
                    <h5 className="card-title">{article.articleTitle}</h5>
                    {/*<h6 className="card-text">By: {this.getAuthorDetails(article.author.id)}</h6>*/}
                    <h6 className="card-text">Article Price: {article.articlePrice} $</h6>
                    <p className="card-text">{article.articleContent}</p>
                   <BuyDialog passArticle={this.passArticle(article)} articleP={this.state.articleP}/>
                  </div>
                </div>
                <br/>
              </center>
              )
          )}
      </>
    );
  }
}
