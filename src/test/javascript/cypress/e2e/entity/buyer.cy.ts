import {
  entityTableSelector,
  entityDetailsButtonSelector,
  entityDetailsBackButtonSelector,
  entityCreateButtonSelector,
  entityCreateSaveButtonSelector,
  entityCreateCancelButtonSelector,
  entityEditButtonSelector,
  entityDeleteButtonSelector,
  entityConfirmDeleteButtonSelector,
} from '../../support/entity';

describe('Buyer e2e test', () => {
  const buyerPageUrl = '/buyer';
  const buyerPageUrlPattern = new RegExp('/buyer(\\?.*)?$');
  const username = Cypress.env('E2E_USERNAME') ?? 'user';
  const password = Cypress.env('E2E_PASSWORD') ?? 'user';
  const buyerSample = {};

  let buyer;

  beforeEach(() => {
    cy.login(username, password);
  });

  beforeEach(() => {
    cy.intercept('GET', '/api/buyers+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/api/buyers').as('postEntityRequest');
    cy.intercept('DELETE', '/api/buyers/*').as('deleteEntityRequest');
  });

  afterEach(() => {
    if (buyer) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/api/buyers/${buyer.id}`,
      }).then(() => {
        buyer = undefined;
      });
    }
  });

  it('Buyers menu should load Buyers page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('buyer');
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('Buyer').should('exist');
    cy.url().should('match', buyerPageUrlPattern);
  });

  describe('Buyer page', () => {
    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(buyerPageUrl);
        cy.wait('@entitiesRequest');
      });

      it('should load create Buyer page', () => {
        cy.get(entityCreateButtonSelector).click();
        cy.url().should('match', new RegExp('/buyer/new$'));
        cy.getEntityCreateUpdateHeading('Buyer');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', buyerPageUrlPattern);
      });
    });

    describe('with existing value', () => {
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/api/buyers',
          body: buyerSample,
        }).then(({ body }) => {
          buyer = body;

          cy.intercept(
            {
              method: 'GET',
              url: '/api/buyers+(?*|)',
              times: 1,
            },
            {
              statusCode: 200,
              headers: {
                link: '<http://localhost/api/buyers?page=0&size=20>; rel="last",<http://localhost/api/buyers?page=0&size=20>; rel="first"',
              },
              body: [buyer],
            }
          ).as('entitiesRequestInternal');
        });

        cy.visit(buyerPageUrl);

        cy.wait('@entitiesRequestInternal');
      });

      it('detail button click should load details Buyer page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('buyer');
        cy.get(entityDetailsBackButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', buyerPageUrlPattern);
      });

      it('edit button click should load edit Buyer page and go back', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('Buyer');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', buyerPageUrlPattern);
      });

      it('edit button click should load edit Buyer page and save', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('Buyer');
        cy.get(entityCreateSaveButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', buyerPageUrlPattern);
      });

      it('last delete button click should delete instance of Buyer', () => {
        cy.get(entityDeleteButtonSelector).last().click();
        cy.getEntityDeleteDialogHeading('buyer').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click();
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', buyerPageUrlPattern);

        buyer = undefined;
      });
    });
  });

  describe('new Buyer page', () => {
    beforeEach(() => {
      cy.visit(`${buyerPageUrl}`);
      cy.get(entityCreateButtonSelector).click();
      cy.getEntityCreateUpdateHeading('Buyer');
    });

    it('should create an instance of Buyer', () => {
      cy.get(`[data-cy="busineessName"]`).type('programming XSS parsing').should('have.value', 'programming XSS parsing');

      cy.get(`[data-cy="phoneNumber"]`).type('radical Wooden').should('have.value', 'radical Wooden');

      cy.get(`[data-cy="email"]`).type('Vilma_McDermott@hotmail.com').should('have.value', 'Vilma_McDermott@hotmail.com');

      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response.statusCode).to.equal(201);
        buyer = response.body;
      });
      cy.wait('@entitiesRequest').then(({ response }) => {
        expect(response.statusCode).to.equal(200);
      });
      cy.url().should('match', buyerPageUrlPattern);
    });
  });
});
