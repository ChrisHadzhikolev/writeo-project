import React from 'react';
import Button from '@material-ui/core/Button';
import TextField from '@material-ui/core/TextField';
import Dialog from '@material-ui/core/Dialog';
import DialogActions from '@material-ui/core/DialogActions';
import DialogContent from '@material-ui/core/DialogContent';
import DialogContentText from '@material-ui/core/DialogContentText';
import DialogTitle from '@material-ui/core/DialogTitle';
import  SellDataService from "../services/sell.service"
import  BuyerDataService from "../services/buyer.service"
import  ArticleDataService from "../services/article.service"

export default function BuyDialog(props) {
    const [open, setOpen] = React.useState(false);

    const handleClickOpen = () => {
        setOpen(true);
    };
    const handleCancel = () => {
        setOpen(false);
    };

    let details = {
        buyerFirstName:"",
        buyerLastName:"",
        card:"",
        cvv:""
    }

    const handlePurchase = () => {
        if(details.buyerFirstName.length <= 0 || details.buyerLastName.length <= 0 || details.card.length !== 16 || details.cvv.length !== 3){
            alert('Incorrect Information! Please, check and try again');
        }else{
            var data = {
                buyerFirstName:details.buyerFirstName,
                buyerLastName:details.buyerLastName,
                buyerSpentMoney:props.articleP.articlePrice
            }
            BuyerDataService.create(data).then(response=>{
                var sellData = {
                    article:props.articleP,
                    buyer:response.data,
                    sellPrice:props.articleP.articlePrice
                }
                    SellDataService.create(sellData).then(()=>{
                        ArticleDataService.articleSold(props.articleP.id).then(response1=>{
                            console.log(response1.status)
                            if (response1.status === 204){
                                alert('success');
                            }
                        }).catch(err=>{
                            if (err.response.status === 500) {
                                window.location.replace("/internal");
                            }
                        })
                    }).catch(err =>{
                        if (err.response.status === 500) {
                            window.location.replace("/internal");
                        }
                    });
            }
            ).catch(err =>{
                if (err.response.status === 500) {
                    window.location.replace("/internal");
                }
            });
            setOpen(false);
            window.location.reload();
        }
    };



    const handleDetails = (e) =>{
        switch (e.target.id) {
            case "buyerFirstName":
                details.buyerFirstName = e.target.value;
                break;
            case "buyerLastName":
                details.buyerLastName = e.target.value;
                break;
            case "card":
                if (e.target.value.length === 16){
                    details.card=e.target.value;
                }else if (e.target.value.length > 16){
                    e.target.value = "";
                    details.card = "";
                    alert('Card numbers have exactly 16 digits!')
                }
                break;
            case "cvv":
                if (e.target.value.length === 3){
                    details.cvv=e.target.value;
                }else if(e.target.value.length > 3){
                    e.target.value = "";
                    details.cvv = "";
                    alert('CVV is exactly 3 digits!')
                }
                break;
        }
        console.log(details);
    }


    return (
        <React.Fragment>
            <div>
                <Button variant="outlined" color="primary" onClick={handleClickOpen}>
                    Buy Article
                </Button>
                <Dialog open={open} onClose={handleCancel} aria-labelledby="form-dialog-title">
                    <DialogTitle id="form-dialog-title">New Purchase</DialogTitle>
                    <DialogContent>
                        <DialogContentText>
                            Please, enter your details here in order to purchase this item. Thank you in advance!
                            <p style={{color:"red"}}>Notice! Do not put any dashes or spaces in the card number.</p>
                        </DialogContentText>
                        <TextField
                            onChange={handleDetails}
                            autoFocus
                            margin="dense"
                            id="buyerFirstName"
                            label="First Name"
                            type="text"
                            fullWidth
                        />
                        <TextField
                            onChange={handleDetails}
                            margin="dense"
                            id="buyerLastName"
                            label="Last Name"
                            type="text"
                            fullWidth
                        />
                        <TextField
                            onChange={handleDetails}
                            margin="dense"
                            id="card"
                            label="Card Number"
                            type="number"
                            fullWidth
                        />
                        <TextField
                            onChange={handleDetails}
                            margin="dense"
                            id="cvv"
                            label="CVV(The number at the back of your credit or debit card)"
                            type="number"
                            maxlength={"3"}
                            fullWidth
                        />
                    </DialogContent>
                    <DialogActions>
                        <Button onClick={handleCancel} color="primary">
                            Cancel
                        </Button>
                        <Button onClick={handlePurchase} color="primary">
                            Buy
                        </Button>
                    </DialogActions>
                </Dialog>
            </div>
        </React.Fragment>
    );
}
