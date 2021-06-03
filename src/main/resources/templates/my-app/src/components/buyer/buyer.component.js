import React, { Component } from "react";
import {Table} from "react-bootstrap";
import BuyerDataService from "../../services/buyer.service";

export default class buyer extends Component {
    constructor(props) {
        super(props);

        this.retrieveBuyers = this.retrieveBuyers.bind(this);

        this.state = {
            buyers:[],
        }
    }
    componentDidMount() {
        this.retrieveBuyers();
    }

    retrieveBuyers = () =>{
        BuyerDataService.getAll().then(response => {
            if(response.status !== 204){
                this.setState({
                    buyers:response.data
                });
            }
        }).catch(err =>{
            if (err.response.status === 500){
                window.location.replace('/internal');
            }else if (err.response.status === 403){
                window.location.replace('/forbidden');
            }
        })
    }

    render() {
        const {buyers} = this.state;

        return(
            <>
                <Table className={"table table-stripped table-dark"}>
                    <thead className={"primary"}>
                    <tr>
                        <th scope={"col"}>First Name</th>
                        <th scope={"col"}>Last Name</th>
                        <th scope={"col"}>Spent Money</th>
                    </tr>
                    </thead>
                    <tbody>
                    {buyers && buyers.map((buyer) =>(
                        <tr>
                            <td style={{width:250}} scope={"row"}>{buyer.buyerFirstName}</td>
                            <td style={{width:250}}>{buyer.buyerLastName}</td>
                            <td style={{width:350}}>{buyer.buyerSpentMoney} $</td>
                        </tr>
                    ))}
                    </tbody>
                </Table>
            </>
        );
    }
}
