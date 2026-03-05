/**
 * File-level: App header component.
 */

import React from 'react';
import {Ban } from 'lucide-react';

/**
 * Header props.
 */
interface HeaderProps {
  title?: string;
}

function Bank(props: { className: string }) {
    return null;
}

/**
 * Header component displays the app brand and optional title.
 */
export default function Header({ title = 'Simple Bank' }: HeaderProps) {
  return (
    <header className="w-full bg-gradient-to-r from-sky-600 to-indigo-600 text-white py-4 shadow-md">
      <div className="max-w-5xl mx-auto px-4 flex items-center gap-3">
        <div className="flex items-center gap-3">
          <div className="p-2 bg-white/20 rounded-md">
            <Bank className="w-6 h-6" />
          </div>
          <div>
            <h1 className="font-semibold text-lg">{title}</h1>
            <p className="text-xs opacity-90">Personal banking demo — local only</p>
          </div>
        </div>
      </div>
    </header>
  );
}