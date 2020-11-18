import React, { Component } from "react";

import UnauthorizedComponent from "../access/unauthorized.component";
import AuthenticationService from "../../services/authentication.service";

export default class OwnerComponent extends Component {
    constructor(props) {
        super(props);

        this.state = {
            authorized:false,
            content: "Owner"
        };
    }

    componentDidMount() {
        const user = AuthenticationService.getCurrentUser()

        if (user && user.roles.includes("ROLE_OWNER")) {
            this.setState({
                authorized:true
            });
        }
    }

    render() {
        return (
            <div className="container">
                {this.state.authorized ? (
                    <header className="jumbotron">
                        <h3>{this.state.content}</h3>
                    </header>
                ) : (
                    <UnauthorizedComponent/>
                )}
            </div>
        );
    }
}