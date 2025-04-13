'use client';

import SidebarLeft from "@/components/landingPage/SidebarLeft";
import MainFeed from "@/components/landingPage/MainFeed";
import SidebarRight from "@/components/landingPage/SidebarRight";

export default function Home() {
  return (
    <main className="flex flex-row justify-between gap-4 px-6 py-4 min-h-screen bg-gray-100 pt-20">
      <SidebarLeft />
      <MainFeed />
      <SidebarRight />
    </main>
  );
}
