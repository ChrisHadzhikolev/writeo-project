import React, {Component} from "react";
import SellDataService from "../../services/sell.service";
import ArticleDataService from "../../services/article.service";
import BuyerDataService from "../../services/buyer.service";

import {Table} from "react-bootstrap";

export default class SellsComponent extends Component{
    constructor(props) {
        super(props);

        this.retrieveSells = this.retrieveSells.bind(this);
        this.deleteSell = this.deleteSell.bind(this);
        this.getArticleTitle = this.getArticleTitle.bind(this);
        this.getBuyerName = this.getBuyerName.bind(this);

        this.state = {
            sells:[],
            articleTitle:"",
            buyerName:""
        }
    }
    componentDidMount() {
        this.retrieveSells();
    }

    retrieveSells = () =>{
        SellDataService.getAll().then(response => {
            if(response.status !== 204){
                this.setState({
                    sells:response.data
                });
            }
        }).catch(err =>{

            // if (err.response.status === 500){
            //     window.location.replace('/internal');
            // }
        })
    }

    deleteSell = (id) =>{
        if (window.confirm('Are you sure you want to delete this sell record?')) {
            SellDataService.delete(id).then(response =>{
                alert('The sell record had been successfully deleted!');
                window.location.reload();
            }).catch(err =>{
                if (err.response.status === 500){
                    window.location.replace('/internal');
                }else if (err.response.status === 403){
                    window.location.replace('/forbidden');
                }
            });
        }
    }

    getArticleTitle = (id) =>{
        console.log(id + "f")
        ArticleDataService.getArticleTitle(id).then(response =>{
            this.state.articleTitle = response.data.articleTitle;
        }).catch(err =>{
            console.log(err);
            if (err.response.status === 500){
                window.location.replace('/internal');
            }
        });
    }
    getBuyerName = (id) =>{
        console.log(id + "ff")
        BuyerDataService.get(id).then(response =>{
            console.log(response.data);
            this.state.buyerName = response.data.buyerFirstName + " " + response.data.buyerLastName;
        }).catch(err =>{
            if (err.response.status === 500){
                window.location.replace('/internal');
            }
        });
    }

    render() {
        const {sells} = this.state;

        return(
            <>
                <Table className={"table table-stripped table-dark"}>
                    <thead className={"primary"}>
                    <tr>
                        <th scope={"col"}>Article Title</th>
                        <th scope={"col"}>Buyer</th>
                        <th scope={"col"}>Date</th>
                        <th scope={"col"}>Cost</th>
                        <th scope={"col"}>Actions</th>
                    </tr>
                    </thead>
                    <tbody>
                    {sells && sells.map((sell) =>(
                        <tr>
                            <td style={{width:250}} scope={"row"}>{this.getArticleTitle(sell.article.id)}{this.state.articleTitle}</td>
                            <td style={{width:250}}>{this.getBuyerName(sell.buyer.id)}{this.state.buyerName}</td>
                            <td style={{width:350}}>{sell.dateOfPurchase}</td>
                            <td style={{width:200}}>{sell.sellPrice}</td>
                            <td><button className={"btn btn-danger"} onClick={()=>this.deleteSell(sell.id)}>Delete</button></td>
                        </tr>
                    ))}
                    </tbody>
                </Table>
            </>
        );
    }
}