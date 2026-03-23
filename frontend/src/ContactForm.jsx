import React, {useRef} from 'react';

function ContactForm({ onSubmit }) {
    const firstNameRef = useRef(null);
    const lastNameRef = useRef(null);

    const handleSubmit = (e) => {
        e.preventDefault();
        const firstName = firstNameRef.current.value;
        const lastName = lastNameRef.current.value;

        const formData = {
            firstName,
            lastName,
        };
        console.log("Submitted:", formData);
        onSubmit(formData)
    };

    return (
        <form onSubmit={handleSubmit}>
            <div>
                <label>First Name:</label><br />
                <input type="text" name="firstName" ref={firstNameRef} />
            </div>
            <div>
                <label>Last Name:</label><br />
                <input type="text" name="lastName" ref={lastNameRef} />
            </div>
            <button type="submit">Submit</button>
        </form>
    );
}

export default ContactForm;