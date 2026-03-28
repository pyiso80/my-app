import React, {useEffect, useState} from "react";
import "./ContactEditModal.css";

function ContactEditModal({contact, onClose, onSave}) {
    const [form, setForm] = useState(contact);

    // If contact changes, sync form
    useEffect(() => {
        setForm(contact);
    }, [contact]);

    const handleChange = (e) => {
        setForm({
            ...form,
            [e.target.name]: e.target.value,
        });
    };

    const handleSubmit = () => {
        onSave(form);
    };

    return (
        <>
            <div className="backdrop">
                <div className="modal">
                    <h2>Edit Contact</h2>

                    <div>
                        <label>First Name:</label><br/>
                        <input
                            name="firstName"
                            value={form.firstName}
                            onChange={handleChange}
                        />
                    </div>
                    <div>
                        <label>Last Name:</label><br/>
                        <input
                            name="lastName"
                            value={form.lastName}
                            onChange={handleChange}
                        />
                    </div>
                    <div>
                        <div>
                            <label>Phone:</label><br/>
                            <input
                                name="phone"
                                value={form.phone}
                                onChange={handleChange}
                            />
                        </div>
                    </div>
                    <div>
                        <label>Email:</label><br/>
                        <input
                            name="email"
                            value={form.email}
                            onChange={handleChange}
                        />
                    </div>
                    <div>
                        <button data-testid="update-contact-button" onClick={handleSubmit}>Save</button>
                        <button onClick={onClose}>Cancel</button>
                    </div>
                </div>
            </div>
        </>
    );
}

export default ContactEditModal;