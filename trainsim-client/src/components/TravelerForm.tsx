import React from "react";
import InputField from "./InputField";

export interface TravelerFormProps {
    traveler: (key: string, v: string ) => void;
}

export default (_: TravelerFormProps) => {
    return (
        <div>
            <div className="columns">
                <div className="column">
                    <InputField
                        label="First Name"
                        htmlName="first-name"
                        htmlType="text"
                        icon="fas fa-user"
                        required={true}
                        value=""
                        setValue={v => _.traveler('firstName', v)}
                    />
                </div>
                <div className="column">
                    <InputField
                        label="Last Name"
                        htmlName="last-name"
                        htmlType="text"
                        icon="fas fa-user"
                        required={true}
                        value=""
                        setValue={v => _.traveler('lastName', v)}
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
                        required={true}
                        value=""
                        setValue={v => _.traveler('email', v)}
                    />
                </div>
                <div className="column">
                    <InputField
                        label="Phone"
                        htmlName="last-name"
                        htmlType="tel"
                        icon="fas fa-phone"
                        required={true}
                        value=""
                        setValue={v => _.traveler('phone', v)}
                    />
                </div>
            </div>
        </div>
    )
};