import {Component} from "react";
import React from "react";
import UserDataService from  "../../services/user.service"
import {Table} from "react-bootstrap";

export default class AuthorsComponent extends Component{
    constructor(props) {
        super(props);

        this.retrieveAuthors = this.retrieveAuthors.bind(this);
        this.deleteAuthor = this.deleteAuthor.bind(this);

        this.state = {
            authors:[],
        }
    }
    componentDidMount() {
        this.retrieveAuthors();
    }

    retrieveAuthors = () =>{
        UserDataService.getAllAuthors().then(response => {
            if(response.status !== 204){
                this.setState({
                    authors:response.data
                });
            }
        }).catch(err =>{
            if (err.response.status === 500){
                window.location.replace('/internal');
            }
        })
    }

    deleteAuthor = (id) =>{
        if (window.confirm('Are you sure you want to delete this profile?')) {
            UserDataService.delete(id).then(() =>{
                alert('The profile had been successfully deleted!');
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

    render() {
        const {authors} = this.state;

        return(
            <>
                <Table className={"table table-stripped table-dark"}>
                    <thead className={"primary"}>
                    <tr>
                        <th scope={"col"}>First Name</th>
                        <th scope={"col"}>Last Name</th>
                        <th scope={"col"}>Email</th>
                        <th scope={"col"}>Gender</th>
                        {/*<th scope={"col"}>Actions</th>*/}
                    </tr>
                    </thead>
                    <tbody>
                    {authors && authors.map((author) =>(
                        <tr>
                            <td style={{width:250}} scope={"row"}>{author.firstName}</td>
                            <td style={{width:250}}>{author.lastName}</td>
                            <td style={{width:350}}>{author.email}</td>
                            <td style={{width:200}}>{author.gender}</td>
                            {/*<td><button className={"btn btn-danger"} onClick={()=>this.deleteAuthor(author.id)}>Delete</button></td>*/}
                        </tr>
                    ))}
                    </tbody>
                </Table>
            </>
        );
    }
}