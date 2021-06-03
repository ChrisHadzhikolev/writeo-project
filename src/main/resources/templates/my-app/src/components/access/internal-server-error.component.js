import React, { Component } from "react";

export default class InternalServerErrorComponent extends Component {
    render() {
        return (
            <div>
                <br />
                <br />
                <br />
                <hr />
                <center>
                    <h1>HTTP Code 500</h1>
                    <h1>Internal Server Error</h1>
                </center>
                <hr />
            </div>
        );
    }
}