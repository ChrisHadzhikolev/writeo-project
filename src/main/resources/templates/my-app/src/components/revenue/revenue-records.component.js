import {Component} from "react";
import React from "react";
import RevenueDataService from "../../services/revenue.service";
import {Table} from "react-bootstrap";

export default class RevenueRecordsComponent extends Component{
    constructor(props) {
        super(props);

        this.retrieveRevenue = this.retrieveRevenue.bind(this);

        this.state = {
            revenueRecords:[],
        }
    }
    componentDidMount() {
        this.retrieveRevenue();
    }

    retrieveRevenue = () =>{
        RevenueDataService.getAll().then(response => {
            if(response.status !== 204){
                this.setState({
                    revenueRecords:response.data
                });
            }
        }).catch(err =>{
            if (err.response.status === 500){
                window.location.replace('/internal');
            }
        })
    }

    render() {
        const {revenueRecords} = this.state;

        return(
            <>
                <div className="jumbotron jumbotron-fluid bg-dark">
                    <div className="container">
                        <h1 className="display-4 text-light">Our Revenue...</h1>
                        <p className="lead text-light">A table showing the accumulated money by month.</p>
                    </div>
                </div>
                <Table className={"table table-stripped table-dark"}>
                    <thead className={"primary"}>
                    <tr>
                        <th scope={"col"}>Month and Year</th>
                        <th scope={"col"}>Revenue</th>
                    </tr>
                    </thead>
                    <tbody>
                    {revenueRecords && revenueRecords.map((revenueRecord) =>(
                        <tr>
                            <td style={{width:250}} scope={"row"}>{revenueRecord.month_and_year}</td>
                            <td style={{width:250}}>{revenueRecord.revenue} $</td>
                        </tr>
                    ))}
                    </tbody>
                </Table>
            </>
        );
    }
}