import { Landmark } from 'lucide-react';

export default function Navbar() {
    return (
        <nav className="fixed top-0 left-0 w-full bg-white shadow-md z-50 px-6 py-4 flex justify-between items-center">
            <div></div>

            <div className="flex items-center gap-2 text-2xl font-bold text-black pl-8">
                <Landmark size={28} />
                <span>Stoa</span>
            </div>

            <button className="bg-blue-600 text-white px-4 py-2 rounded hover:bg-blue-700 transition">
                Login
            </button>
        </nav>
    );
}
