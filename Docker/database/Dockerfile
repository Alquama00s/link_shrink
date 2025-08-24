FROM docker.io/library/postgres:latest

# Set environment variables for PostgreSQL
ENV POSTGRES_USER=backend
ENV POSTGRES_PASSWORD=pass_876
ENV POSTGRES_DB=linkshrink

# Copy SQL scripts to the initialization directory
COPY Docker/database/sql/ /docker-entrypoint-initdb.d/

# Expose the default PostgreSQL port
EXPOSE 5432