const generateRID = () => {
  return Date.now() + Math.floor(Math.random() * 10);
};

export { generateRID };
