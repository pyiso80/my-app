import React from 'react';

function ContactTable({ contacts, onDelete, onUpdate }) {
    return (
        <table id="contact-table">
            <tbody>
            {contacts.map(contact => (
                <tr key={contact.id}>
                    <td>{contact.firstName}</td>
                    <td>{contact.lastName}</td>
                    <td>{contact.phone}</td>
                    <td>{contact.email}</td>
                    <td>
                        <button
                            type="button"
                            data-testid="delete-contact"
                            onClick={() => onDelete(contact.id)}
                        >
                            Delete
                        </button>
                    </td>
                    <td>
                        <button
                            type="button"
                            data-testid="update-contact"
                            onClick={() => onUpdate(contact.id)}
                        >
                            Edit
                        </button>
                    </td>
                </tr>
            ))}
            </tbody>
        </table>
    );
}
export default ContactTable;