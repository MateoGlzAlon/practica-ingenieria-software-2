"use server";

import { S3Client, PutObjectCommand } from "@aws-sdk/client-s3"
import { getSignedUrl } from "@aws-sdk/s3-request-presigner"
import crypto from "crypto"

const generateFileName = (bytes = 32) => crypto.randomBytes(bytes).toString("hex")
const s3 = new S3Client({
    region: process.env.AWS_BUCKET_REGION,
    credentials: {
        accessKeyId: process.env.AWS_ACCESS_KEY,
        secretAccessKey: process.env.AWS_SECRET_ACCESS_KEY
    }
})

const acceptedTypes = [
    "image/jpeg",
    "image/jpg",
    "image/png",
    "image/webp",
]

const maxFileSize = 1024 * 1024 * 5; //5MB

export default async function getSignedURLAction(fileType, fileSize, checksum) {

    if (!acceptedTypes.includes(fileType)) {
        return { failure: "Invalid file type" }
    }

    if (fileSize > maxFileSize) {
        return { failure: "File too large" }
    }

    const putObjectCommand = new PutObjectCommand({
        Bucket: process.env.AWS_BUCKET_NAME,
        Key: generateFileName(),
        ContentType: fileType,
        ContentLength: fileSize,
        ChecksumSHA256: checksum,
    })

    const signedURL = await getSignedUrl(s3, putObjectCommand, { expiresIn: 60 })

    return {
        success: {
            url: signedURL
        }
    }
}