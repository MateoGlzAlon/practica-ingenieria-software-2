FROM postgres:15

# Use default postgres superuser
ENV POSTGRES_PASSWORD=stoa_password
ENV POSTGRES_DB=stoa_db

# Copy your full schema + data
COPY entire.sql /docker-entrypoint-initdb.d/

EXPOSE 5432
