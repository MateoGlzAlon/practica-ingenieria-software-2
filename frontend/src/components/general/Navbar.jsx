import Link from "next/link";  // Make sure to import Link for navigation
import { Landmark } from 'lucide-react';

export default function Navbar() {
    return (
        <>
            <nav className="fixed top-0 left-0 w-full bg-white shadow-md z-50 px-6 py-4 flex justify-between items-center">
                <div />

                <div className="flex items-center text-2xl font-bold text-black pl-8 hover:cursor-pointer">
                    <Link href="/" className="flex items-center gap-2">
                        <Landmark size={28} />
                        <span>Stoa</span>
                    </Link>
                </div>

                <button className="bg-blue-600 text-white px-4 py-2 rounded hover:bg-blue-700 transition">
                    Login
                </button>
            </nav>

            <div className="mt-20"></div>
        </>
    );
}
