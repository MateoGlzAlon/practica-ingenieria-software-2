import axios from 'axios';
import { DATA } from "@/app/data"

export default async function getTopUsersStats() {
    try {
        const response = await axios.get(`${DATA.apiURL}/stats/top-users`, {
            headers: {
                'Content-Type': 'application/json',
            },
        });

        return response.data;
    } catch (error) {
        console.error("Error fetching community stats:", error);
        throw error;
    }
}