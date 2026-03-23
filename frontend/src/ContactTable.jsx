import React from 'react';

function ContactTable({ contacts }) {
    return (
        <table id="contact-table">
            <tbody>
            {contacts.map(contact => (
                <tr key={contact.id}>
                    <td>{contact.name}</td>
                </tr>
            ))}
            </tbody>
        </table>
    );
}

export default ContactTable;