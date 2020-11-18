import React, { Component } from "react";

import AuthenticationService from "../../services/authentication.service";
import UnauthorizedComponent from "../access/unauthorized.component";

export default class AuthorComponent extends Component {
    constructor(props) {
        super(props);

        this.state = {
            authorized:false,
            content: "Author"
        };
    }

    componentDidMount() {
        const user = AuthenticationService.getCurrentUser()

        if (user && user.roles.includes("ROLE_AUTHOR")) {
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