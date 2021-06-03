import UserDataService from "../services/user.service";
import {Table} from "react-bootstrap";
import React, {Component} from "react";

export default class AdminComponent extends Component{
    constructor(props) {
        super(props);

        this.retrieveAdmins = this.retrieveAdmins.bind(this);
        this.deleteAdmin = this.deleteAdmin.bind(this);

        this.state = {
            admins:[],
        }
    }
    componentDidMount() {
        this.retrieveAdmins();
    }

    retrieveAdmins = () =>{
        UserDataService.getAllAdmins().then(response => {
            if(response.status !== 204){
                this.setState({
                    admins:response.data
                });
                console.log(response.data)
            }
        }).catch(err =>{
            if (err.response.status === 500){
                window.location.replace('/internal');
            }
        })
    }

    deleteAdmin = (id) =>{

        if (window.confirm('Are you sure you want to delete this profile?')) {
            UserDataService.delete(id).then(response =>{
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
        const {admins} = this.state;

        return(
            <>
                <Table className={"table table-stripped table-dark"}>
                    <thead className={"primary"}>
                    <tr>
                        <th scope={"col"}>First Name</th>
                        <th scope={"col"}>Last Name</th>
                        <th scope={"col"}>Email</th>
                        <th scope={"col"}>Gender</th>
                        <th scope={"col"}>Actions</th>
                    </tr>
                    </thead>
                    <tbody>
                    {admins && admins.map((admin) =>(
                        <tr>
                            <td style={{width:250}} scope={"row"}>{admin.firstName}</td>
                            <td style={{width:250}}>{admin.lastName}</td>
                            <td style={{width:350}}>{admin.email}</td>
                            <td style={{width:200}}>{admin.gender}</td>
                            <td><button className={"btn btn-danger"} onClick={()=>this.deleteAdmin(admin.id)}>Delete</button></td>
                        </tr>
                    ))}
                    </tbody>
                </Table>
            </>
        );
    }
}
