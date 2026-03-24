import React, {useRef} from 'react';

function ContactForm({ setContacts }) {
    const firstNameRef = useRef(null);
    const lastNameRef = useRef(null);
    const emailRef = useRef(null);
    const phoneRef = useRef(null);

    const handleSubmit = async (e) => {
        e.preventDefault();
        const firstName = firstNameRef.current.value;
        const lastName = lastNameRef.current.value;
        const phone = phoneRef.current.value;
        const email = emailRef.current.value;

        const formData = {
            firstName,
            lastName,
            phone,
            email,
        };
        try {
            const response = await fetch('/api', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify(formData),
            });

            const data = await response.json();
            console.log(data)
            setContacts(data);
        } catch (error) {
            console.error('Error:', error);
        }
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
            <div>
                <label>Phone:</label><br />
                <input type="text" name="phone" ref={phoneRef} />
            </div>
            <div>
                <label>Email:</label><br />
                <input type="email" name="email" ref={emailRef} />
            </div>
            <button type="submit">Submit</button>
        </form>
    );
}

export default ContactForm;