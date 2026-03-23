import React from 'react';

function ContactTable({ contacts }) {
    return (
        <table id="contact-table">
            <tbody>
            {contacts.map(contact => (
                <tr key={contact.id}>
                    <td>{contact.firstName}</td>
                    <td>{contact.lastName}</td>
                    <td>{contact.phone}</td>
                    <td>{contact.email}</td>
                </tr>
            ))}
            </tbody>
        </table>
    );
}

export default ContactTable;