import axios from 'axios';
import getSignedURLAction from '@/components/awsComponents/S3Actions';

const computeSHA256 = async (file) => {
    const buffer = await file.arrayBuffer();
    const hashBuffer = await crypto.subtle.digest("SHA-256", buffer);
    const hashArray = Array.from(new Uint8Array(hashBuffer));
    const hashHex = hashArray
        .map((b) => b.toString(16).padStart(2, "0"))
        .join("");
    return hashHex;
};

export const uploadFile = async (file) => {
    if (!file) {
        throw new Error('No file provided for upload.');
    }

    try {
        // Compute file checksum
        const checksum = await computeSHA256(file);

        // Get signed URL from server
        const signedURLResult = await getSignedURLAction(file.type, file.size, checksum);
        if (signedURLResult.failure) {
            throw new Error(`Failed to fetch signed URL: ${signedURLResult.failure}`);
        }

        const url = signedURLResult.success.url;

        // Upload the file to S3 using the signed URL
        await axios.put(url, file, {
            headers: {
                'Content-Type': file.type,
            },
        });

        return { success: true, message: 'File uploaded successfully!', url: url.split("?"[0]) };
    } catch (error) {
        if (axios.isAxiosError(error)) {
            throw new Error(`File upload failed: ${error.response?.data?.message || error.message}`);
        } else {
            throw new Error(error.message || 'An unexpected error occurred.');
        }
    }
};
