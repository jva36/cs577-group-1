import React, {Component, ReactElement} from "react";
import Itinerary from "../models/Itinerary";
import ItinerarySearch from "../models/ItinerarySearch";
import PurchaseStage from "../models/PurchaseStage";
import InputField from "./InputField";
import NavButtonBar from "./NavButtonBar";
import ProgressTracker from "./PurchaseTracker";
import SearchHeader from "./SearchHeader";
import TravelerInfoPage from "./TravelerInfoPage";
import CreditCard from "../models/CreditCard";
import PaymentProvider from "../providers/PaymentProvider";
import Payment from "../models/Payment";
import ConfirmationPage from "./ConfirmationPage";
import {AuthContext} from "../contexts/AuthContext";
import CheckoutProvider from "../providers/CheckoutProvider";
import Order from "../models/Order";
import {Ticket} from "../models/Ticket";
import {Address} from "../models/Address";
import {Traveler} from "../models/Traveler";

export interface CheckoutPageProps {
    search: ItinerarySearch;
    itinerary: Itinerary;
    setPage: (page: ReactElement) => void;
    travelers: Array<Traveler>;
}

interface CheckoutPageState {
    cardHolder: string;
    cardNumber: string;
    cardExp: string;
    cvv: string;
    firstName: string;
    lastName: string;
    email: string;
    phoneNum: string;
    address: string;
    addressApt?: string;
    city: string;
    state: string;
    zip: string;
}

export default class CheckoutPage extends Component<CheckoutPageProps, CheckoutPageState> {
    private readonly paymentProvider: PaymentProvider;
    private readonly checkoutProvider: CheckoutProvider;

    constructor(props: CheckoutPageProps) {
        super(props);
        this.state = {
            cardHolder: 'Test Tester',
            cardNumber: '3456345634563456',
            cardExp: '10/24',
            cvv: '123',
            firstName: 'test',
            lastName: 'tester',
            email: 'test@gmail.com',
            phoneNum: '5555555555',
            address: '123 Street Rd.',
            addressApt: '',
            city: 'Chicago',
            state: 'PA',
            zip: '19000'
        };
        CheckoutPage.contextType = AuthContext;
        this.paymentProvider = new PaymentProvider();
        this.checkoutProvider = new CheckoutProvider();
        this.submit = this.submit.bind(this);
    }

    submit() {
        const price = 31; // Default price per ticket
        const totalPrice = getPrice(this.props.travelers.length); // Price is not yet included in any fetch-able data. For now use travelers * default price = total
        const payment = new Payment(new CreditCard(this.state.cardNumber, this.state.cvv), totalPrice);
        const { search, itinerary, travelers } = this.props;
        const { address, addressApt, city, state, zip, email } = this.state;
        this.paymentProvider.doPayment(payment, paymentResponse => {
            if (paymentResponse.code === 0) {
                const tickets: Ticket[] = [];
                travelers.forEach(() => {
                    tickets.push(new Ticket(itinerary.id, price));
                });
                const orderAddress: Address = new Address(address, city, state, zip, addressApt);
                const order: Order = new Order('paid', this.context.user.userId, tickets, travelers, orderAddress);
                this.checkoutProvider.doCheckout(order, orderResponse => {
                    this.props.setPage(<ConfirmationPage orderResponse={orderResponse} orderEmail={email} search={search} itinerary={itinerary} />)
                });
            }
        });
    }

    override render() {
        const {search, itinerary, setPage} = this.props;

        return <div>
            <SearchHeader search={search}/>
            <ProgressTracker currentStage={PurchaseStage.Checkout}/>

            <hr/>

            <div className="content">
                <h2 className="title is-3">Checkout</h2>
                <p>Please fill out the following billing information.</p>
                <div className="columns">
                    <div className="column is-8">
                        <form>
                            <div className="columns">
                                <div className="column">
                                    <InputField
                                        label="Cardholder Name"
                                        htmlName="cardholder-name"
                                        htmlType="text"
                                        icon="fas fa-user"
                                        autoComplete="cc-name"
                                        required={true}
                                        value={this.state.cardHolder}
                                        setValue={v => this.setState({ cardHolder: v })}
                                    />
                                </div>
                            </div>
                            <div className="columns">
                                <div className="column">
                                    <InputField
                                        label="Card Number"
                                        htmlName="card-number"
                                        htmlType="text"
                                        icon="fas fa-credit-card"
                                        autoComplete="cc-number"
                                        required={true}
                                        value={this.state.cardNumber}
                                        setValue={v => this.setState({ cardNumber: v })}
                                    />
                                </div>
                            </div>
                            <div className="columns">
                                <div className="column">
                                    <InputField
                                        label="Expiration"
                                        htmlName="expiration"
                                        htmlType="text"
                                        icon="fas fa-calendar"
                                        autoComplete="cc-exp"
                                        required={true}
                                        value={this.state.cardExp}
                                        setValue={v => this.setState({ cardExp: v })}
                                    />
                                </div>
                                <div className="column">
                                    <InputField
                                        label="Security Code"
                                        htmlName="security-code"
                                        htmlType="password"
                                        icon="fas fa-lock"
                                        autoComplete="cc-csc"
                                        required={true}
                                        value={this.state.cvv}
                                        setValue={v => this.setState({ cvv: v })}
                                    />
                                </div>
                            </div>
                            <hr/>
                            <div className="columns">
                                <div className="column">
                                    <InputField
                                        label="First Name"
                                        htmlName="first-name"
                                        htmlType="text"
                                        icon="fas fa-user"
                                        autoComplete="billing fname"
                                        required={true}
                                        value={this.state.firstName}
                                        setValue={v => this.setState({ firstName: v })}
                                    />
                                </div>
                                <div className="column">
                                    <InputField
                                        label="Last Name"
                                        htmlName="last-name"
                                        htmlType="text"
                                        icon="fas fa-user"
                                        autoComplete="billing lname"
                                        required={true}
                                        value={this.state.lastName}
                                        setValue={v => this.setState({ lastName: v })}
                                    />
                                </div>
                            </div>
                            <div className="columns">
                                <div className="column">
                                    <InputField
                                        label="Email"
                                        htmlName="email"
                                        htmlType="email"
                                        icon="fas fa-at"
                                        autoComplete="billing email"
                                        required={true}
                                        value={this.state.email}
                                        setValue={v => this.setState({ email: v })}
                                    />
                                </div>
                                <div className="column field">
                                    <InputField
                                        label="Phone"
                                        htmlName="phone"
                                        htmlType="tel"
                                        icon="fas fa-phone"
                                        autoComplete="billing tel"
                                        required={true}
                                        value={this.state.phoneNum}
                                        setValue={v => this.setState({ phoneNum: v })}
                                    />
                                </div>
                            </div>
                            <div className="columns">
                                <div className="column">
                                    <InputField
                                        label="Street Address"
                                        htmlName="address-line1"
                                        htmlType="text"
                                        icon="fas fa-map-marker-alt"
                                        autoComplete="billing address-line1"
                                        required={true}
                                        value={this.state.address}
                                        setValue={v => this.setState({ address: v })}
                                    />
                                </div>
                                <div className="column">
                                    <InputField
                                        label="Apartment or Suite (optional)"
                                        htmlName="address-line2"
                                        htmlType="text"
                                        icon="fas fa-map-marker-alt"
                                        autoComplete="billing address-line2"
                                        required={false}
                                        value=""
                                        setValue={v => this.setState({ addressApt: v })}
                                    />
                                </div>
                            </div>
                            <div className="columns">
                                <div className="column">
                                    <InputField
                                        label="City"
                                        htmlName="city"
                                        htmlType="text"
                                        icon="fas fa-city"
                                        autoComplete="billing locality"
                                        required={true}
                                        value={this.state.city}
                                        setValue={v => this.setState({ city: v })}
                                    />
                                </div>
                                <div className="column">
                                    <InputField
                                        label="State"
                                        htmlName="state"
                                        htmlType="text"
                                        icon="fas fa-map"
                                        autoComplete="billing region"
                                        required={true}
                                        value={this.state.state}
                                        setValue={v => this.setState({ state: v })}
                                    />
                                </div>
                                <div className="column">
                                    <InputField
                                        label="Zip Code"
                                        htmlName="zip-code"
                                        htmlType="text"
                                        icon="fas fa-map-pin"
                                        autoComplete="billing postal-code"
                                        required={true}
                                        value={this.state.zip}
                                        setValue={v => this.setState({ zip: v })}
                                    />
                                </div>
                            </div>
                            {/* <label className="checkbox">
                                <input type="checkbox" />
                                Save this card for future purchases
                            </label>
                            <input type="submit" className="button is-primary is-pulled-right" value="Add Payment Info" /> */}
                        </form>
                    </div>
                    <div className="column">
                        <div className="box">
                            <div className="is-size-5 has-text-weight-bold">Order Summary</div>
                            <hr/>
                            <div className="columns p-1">
                                <div className="column has-text-weight-bold">
                                    <p className="heading">Ticket</p>
                                    <p>{search.source.name}
                                        <span className="icon is-size-6"><i className="fas fa-long-arrow-alt-right"></i></span>
                                        {search.target.name}</p>
                                </div>
                                <div className="column has-text-weight-bold has-text-right">
                                    <p className="heading">Quantity</p>
                                    <p>x {this.props.search.travelers}</p>
                                </div>
                            </div>
                            <hr/>
                            <div className="columns p-1">
                                <div className="column">
                                    <p className="">Fare</p>
                                </div>
                                <div className="column has-text-right">
                                    <p className="">{`$${getPrice(this.props.search.travelers).toFixed(2)}`}</p>
                                </div>
                            </div>
                            <hr/>
                            <div className="columns p-1">
                                <div className="column has-text-weight-bold">
                                    <p className="">Total</p>
                                </div>
                                <div className="column has-text-weight-bold has-text-right">
                                    <p className="">{`$${getPrice(this.props.search.travelers).toFixed(2)}`}</p>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <NavButtonBar
                onBack={() => setPage(<TravelerInfoPage search={search} itinerary={itinerary} setPage={setPage}/>)}
                onSubmit={() => this.submit()}
            />
        </div>
    }
}

/**
 *
 * @param travelers - number of travelers that need tickets
 * @param price - price of the ticket
 *
 * The price was hardcoded as 31 for all tickets. Price remains 31, however, function will accept a parameter
 * for price if this data is provided at a later time.
 */
function getPrice(travelers: number, price: number = 31) {
    return (travelers * price);
}