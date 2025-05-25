import Link from "next/link";
import { Landmark } from "lucide-react";

export default function Navbar() {
    return (
        <>
            <nav className="fixed top-0 left-0 w-full bg-white border-b-[0.5px] border-gray-500 z-50 px-6 py-4 flex justify-between items-center">
                <div />

                <Link href="/" className="flex items-center">
                    <div className="flex items-center text-2xl font-bold text-black hover:cursor-pointer">
                        <Landmark size={28} />
                        <span className="pl-2 text-2xl">Stoa</span>
                    </div>
                </Link>

                <Link href="/login">
                    <button className="bg-blue-600 text-white px-4 py-2 rounded hover:bg-blue-700 transition">
                        Login
                    </button>
                </Link>
            </nav>

            <div className="mt-[72px]"></div>
        </>
    );
}
