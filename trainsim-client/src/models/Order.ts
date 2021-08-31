import {Address} from "./Address";
import {Ticket} from "./Ticket";
import {Traveler} from "./Traveler";

export default class Order {
    private readonly _paymentConfirmation: string;
    private readonly _userId: number;
    private readonly _ticket: Ticket;
    private readonly _travelers: Array<Traveler>;
    private readonly _address: Address;

    constructor(paymentConfirmation: string, userId: number, ticket: Ticket, travelers: Array<Traveler>, address: Address) {
        this._paymentConfirmation = paymentConfirmation;
        this._userId = userId;
        this._ticket = ticket;
        this._travelers = travelers;
        this._address = address;

        this.toJson = this.toJson.bind(this);
    }

    get travelers() {
        return this._travelers;
    }

    public toJson() {

        return JSON.stringify({
            paymentConfirmation: this._paymentConfirmation,
            userId: this._userId,
            ticket: this._ticket,
            travelers: this._travelers,
            address: this._address
        });
    }
}