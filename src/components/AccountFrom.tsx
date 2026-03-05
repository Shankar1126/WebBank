/**
 * File-level: Account creation form component.
 */

import React, { useState } from 'react';
import { v4 as uuid } from 'uuid';
import { Account } from '../types';
import { loadAccounts, saveAccounts } from '../lib/storage';

/**
 * Props for AccountForm.
 */
interface AccountFormProps {
  onCreate: (account: Account) => void;
}

/**
 * AccountForm allows creating a new account and persists it.
 */
export default function AccountForm({ onCreate }: AccountFormProps) {
  const [name, setName] = useState('');
  const [email, setEmail] = useState('');
  const [initial, setInitial] = useState<number | ''>('');

  /**
   * Handle submission to create and persist an account.
   */
  function handleSubmit(e: React.FormEvent) {
    e.preventDefault();
    const balance = Number(initial || 0);
    const account: Account = {
      id: uuid(),
      name: name.trim() || 'Unnamed',
      email: email.trim() || '',
      balance,
      createdAt: new Date().toISOString(),
    };
    const accounts = loadAccounts();
    const next = [account, ...accounts];
    saveAccounts(next);
    onCreate(account);
    setName('');
    setEmail('');
    setInitial('');
  }

  return (
    <form onSubmit={handleSubmit} className="bg-white rounded-lg shadow p-4 space-y-3">
      <h2 className="text-lg font-semibold">Create Account</h2>
      <div className="grid grid-cols-1 md:grid-cols-3 gap-3">
        <input
          required
          value={name}
          onChange={e => setName(e.target.value)}
          placeholder="Full name"
          className="col-span-1 md:col-span-1 p-2 border rounded"
        />
        <input
          required
          value={email}
          onChange={e => setEmail(e.target.value)}
          placeholder="Email"
          className="col-span-1 md:col-span-1 p-2 border rounded"
          type="email"
        />
        <input
          value={initial}
          onChange={e => setInitial(e.target.value === '' ? '' : Number(e.target.value))}
          placeholder="Initial deposit"
          className="col-span-1 md:col-span-1 p-2 border rounded"
          type="number"
          min="0"
        />
      </div>
      <div className="flex justify-end">
        <button className="bg-indigo-600 text-white px-4 py-2 rounded hover:bg-indigo-700">
          Create Account
        </button>
      </div>
    </form>
  );
}